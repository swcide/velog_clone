package com.clone.velog.service;

import com.clone.velog.web.domain.comment.Comment;
import com.clone.velog.web.dto.response.comment.CommentResponseDto;
import com.clone.velog.web.dto.response.member.MemberResponseDto;
import com.clone.velog.web.dto.response.posting.PostingAllByMemberResponseDto;
import com.clone.velog.web.dto.response.posting.PostingDetailResponseDto;
import com.clone.velog.web.dto.response.posting.PostingResponseDto;
import com.clone.velog.web.dto.response.tag.TagNameAndCount;
import com.clone.velog.web.domain.tag.Tags;
import com.clone.velog.web.domain.tag.TagsRepository;
import com.clone.velog.web.dto.request.PostingRequestDto;
import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.comment.CommentRepository;
import com.clone.velog.web.domain.member.MemberRepository;
import com.clone.velog.web.domain.posting.PostingRepository;
import com.clone.velog.web.dto.response.tag.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


@RequiredArgsConstructor
@Service
public class PostingService {

    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TagsRepository tagsRepository;


    // 게시글 생성 and 태그 생성
    @Transactional
    public Long createPosting(PostingRequestDto postingRequestDto) {
        Member member = getMemberById(postingRequestDto.getMemberId());
        List<Tags> tags = Tags.createTag(postingRequestDto.getTagList().getStringTagName());
        dupTag(tags);
        Posting posting = Posting.createPosting(member,postingRequestDto,tags);

        postingRepository.save(posting);

        return posting.getPostingId();
    }


    // 전체 게시글 조회 (최적화)
    public List<PostingResponseDto> getAllPosting(int page, int size) {
        List<Posting> postPaging = postingRepository.findAllPaging(PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        return postPaging
                        .stream()
                        .map(PostingResponseDto::new)
                        .collect(toList());
    }

//     내 게시물 전체 목록
    public PostingAllByMemberResponseDto getMemberPostings(Long memberId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Member member = getMemberById(memberId);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);

        List<Posting> findPostingByMember = postingRepository.findAllMemberId(memberId,pageable);

        List<PostingResponseDto> postingResponseDto = findPostingByMember
                .stream()
                .map(PostingResponseDto::new)
                .collect(toList());

        List<TagNameAndCount> tagList = tagsRepository.findAll(memberId);

        List<TagResponseDto> tagResponseDto = tagList
                .stream()
                .map(TagResponseDto::new)
                .collect(toList());

        return new PostingAllByMemberResponseDto(postingResponseDto,tagResponseDto,memberResponseDto);

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

    // 게시물 수정
    @Transactional
    public Long updatePosting(Long postId, PostingRequestDto postingRequestDto, String memberEmail) {
        // 게시물 존재 여부 확인
        Posting posting = getPost(postId);
        Member member = getMemberByEmail(memberEmail);

        // 업데이트 태그(리퀘스트)
        List<Tags> updateTags = Tags.updateTag(postingRequestDto.getTagList().getStringTagName());

        // 권한 체크
        validateMember(member, postingRequestDto.getMemberId());

        // 현재 태그
        List<Tags> tags = getTag(posting);
        tagsRepository.deleteAll(tags);

        // 태그 중복확인
        dupTag(updateTags);
        posting.updatePosting(postingRequestDto,updateTags);
        return posting.getPostingId();
    }

    // 게시글 삭제
    @Transactional
    public Long deletePosting(Long postId, String memberEmail) {
        Posting posting = getPost(postId);
        Member member = getMemberByEmail(memberEmail);
        
        validateMember(member,posting.getMember().getMemberId());
        
        posting.deletePosting();
        
        return postId;   
    }


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
    private void dupTag(List<Tags> tagsList) {
        for(Tags t : tagsList){
            Tags dupTag = tagsRepository.findTagsByTagNameAndPosting(t.getTagName(),t.getPosting());
            if(dupTag != null){
                throw new IllegalArgumentException("중복된 태그");
            }
        }
    }


  
}
