package com.clone.velog.service;

import com.clone.velog.web.domain.comment.Comment;
import com.clone.velog.web.dto.response.TagNameAndCount;
import com.clone.velog.web.domain.tag.Tags;
import com.clone.velog.web.domain.tag.TagsRepository;
import com.clone.velog.web.dto.response.*;
import com.clone.velog.web.dto.request.PostingRequestDto;
import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.comment.CommentRepository;
import com.clone.velog.web.domain.member.MemberRepository;
import com.clone.velog.web.domain.posting.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostingService {

    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TagsRepository tagsRepository;


    private List<Tags> getTag(Posting posting) {
        return tagsRepository.findAllByPosting(posting);
    }


    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(()->new ApiRequestException("조회된 멤버가 없습니다"));
    }

    private Member getMemberByEmail(String memberEmail) {
        return memberRepository.findByEmail(memberEmail).orElseThrow(()-> new ApiRequestException("찾는 유저 없습니다."));
    }

    private Posting getPost(Long postId) {
        return postingRepository.findById(postId).orElseThrow(()-> new ApiRequestException("해당 게시물이 존재하지 않습니다."));
    }

    private void validateMember(Member member, Long memberId) {
        if (!memberId.equals(member.getMemberId())) {
            throw new ApiRequestException("해당 게시물에 대한 수정 권한이 없습니다.");
        }
    }

    private void dupTag(Posting posting) {
        for(Tags t : posting.getTags()){
            Tags dupTag = tagsRepository.findTagsByTagNameAndPosting(t.getTagName(),posting);
            if(dupTag != null){
                throw new IllegalArgumentException("중복된 태그");
            }
        }
    }


    // 게시글 생성 and 태그 생성
    @Transactional
    public Long createPosting(PostingRequestDto postingRequestDto) {
        Member member = getMemberById(postingRequestDto.getMemberId());

        List<Tags> tags = Tags.createTag(postingRequestDto.getTagList().getStringTagName());

        Posting posting = Posting.createPosting(member,postingRequestDto,tags);
        System.out.println(posting.getTitle());

//
        postingRepository.save(posting);


        return posting.getPostingId();
    }



    // 전체 게시글 조회 (최적화)
    public List<PostingResponseDto> getAllPosting(int page, int size) {
        List<Posting> postPaging = postingRepository.findAllPaging(PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        return postPaging
                        .stream()
                        .map(PostingResponseDto::new)
                        .collect(Collectors.toList());
    }

//     내 게시물 전체 목록
    public  PostingUserResponseDto getMemberPostings(Long memberId, int page, int size) {

        Pageable pageable = PageRequest.of(page ,size);

        Member member = getMemberById(memberId);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);

        List<Posting> findPostingByMember = postingRepository.findAllMemberId(memberId,pageable);
        List<PostingResponseDto> postingResponseDto = findPostingByMember.stream().map(PostingResponseDto::new).collect(Collectors.toList());

        List<TagNameAndCount> tagList = tagsRepository.findAll(memberId);
        List<TagResponseDto> tagResponseDto = tagList.stream().map(tag->new TagResponseDto(tag)).collect(Collectors.toList());


        PostingUserResponseDto postingUserResponseDto =new PostingUserResponseDto(postingResponseDto,tagResponseDto,memberResponseDto);

        return postingUserResponseDto;
    }

    // 게시물 상세
    public PostingDetailResponseDto getPostingDetail(Long postId) {
        Posting posting = getPost(postId);
        List<Comment> commentList = commentRepository.findAllByPostingOrderByCreatedAtDesc(posting);
        List<CommentResponseDto> commentResponseDtoList = commentList
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new PostingDetailResponseDto(posting, commentResponseDtoList);
    }
}
