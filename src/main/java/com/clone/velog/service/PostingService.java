package com.clone.velog.service;


import com.clone.velog.web.dto.response.TagNameAndCount;
import com.clone.velog.web.domain.tag.Tags;
import com.clone.velog.web.domain.tag.TagsRepository;
import com.clone.velog.web.dto.response.*;
import com.clone.velog.web.dto.request.PostingRequestDto;
import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.web.domain.comment.Comment;
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


    //게시글 전체
    public List<PostingResponseDto> getAllPosting(int page,int size){

        Page<Posting> pagePaging = postingRepository.findAll(PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        List<PostingResponseDto> postingResponseDto = pagePaging.getContent().stream().map(PostingResponseDto::new).collect(Collectors.toList());


        return postingResponseDto;
    }

    // 내 게시물 전체 목록
    public  PostingAllResponseDto getMemberPostings(Long memberId, int page, int size) {

        List<TagNameAndCount> tagResponseDto = tagsRepository.findAll(memberId);
        MemberResponseDto memberResponseDto = new MemberResponseDto(getMemberById(memberId));

        Page<Posting> postings = postingRepository.findAll(PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        List<PostingResponseDto> postingResponseDto = postings
                .stream()
                .map(PostingResponseDto::new)
                .collect(Collectors.toList());

        PostingAllResponseDto postingAllResponseDto = new PostingAllResponseDto(postingResponseDto,tagResponseDto,memberResponseDto);
        return postingAllResponseDto;
    }

    // 게시물 상세
    public PostingDetailResponseDto getPostingDetail(Long postId) {
        Posting posting = getPost(postId);
        List<TagResponseDto> tagResponseDto = getTag(posting).stream().map(TagResponseDto::new).collect(Collectors.toList());

        List<Comment> commentList = commentRepository.findAllByPostingOrderByCreatedAtDesc(posting);
        List<CommentResponseDto> commentResponseDtoList = commentList
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new PostingDetailResponseDto(posting, commentResponseDtoList,tagResponseDto);
    }


    // 게시물 등록
    @Transactional
    public Long createPosting(PostingRequestDto postingRequestDto) {
        Member member = getMemberById(postingRequestDto.getMemberId());
        Posting posting = postingRequestDto.createPost(member);


        //포스트 flush -> postId가 존재
        postingRepository.save(posting);
        List<Tags> tags = Tags.createTag(posting,postingRequestDto);

        dupTag(posting, tags);
        tagsRepository.saveAll(tags);


        return posting.getPostingId();
    }



    // 게시물 수정
    @Transactional
    public Long updatePosting(Long postId, PostingRequestDto postingRequestDto, String memberEmail) {
        // 게시물 존재 여부 확인

        Posting posting = getPost(postId);
        Member member = getMemberByEmail(memberEmail);

        // 업데이트 태그
        List<Tags> updateTags = Tags.createTag(posting,postingRequestDto);

        // 현재 태그
        List<Tags> tags = getTag(posting);



        // 게시자 확인
        validateMember(member, postingRequestDto.getMemberId());


        tagsRepository.deleteAll(tags);

        // 태그 중복확인
        dupTag(posting, updateTags);
        tagsRepository.saveAll(updateTags);

        posting.updatePosting(postingRequestDto);
        return posting.getPostingId();
    }




    // 게시물 삭제
    @Transactional
    public Long deletePosting(Long postId, String memberEmail){
        Posting posting = getPost(postId);
        Member member = getMemberByEmail(memberEmail);

        // 작성자 체크
        validateMember(member, postId);

        // 현재 게시물 태그
        List<Tags> tags = getTag(posting);


        tagsRepository.deleteByPosting(posting);
        posting.deletePosting();
        return getPost(postId).getPostingId();
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

    private void dupTag(Posting posting, List<Tags> tags) {
        for(Tags t : tags){
            Tags dupTag = tagsRepository.findTagsByTagNameAndPosting(t.getTagName(),posting);
            if(dupTag != null){
                throw new IllegalArgumentException("중복된 태그");
            }
        }
    }

}
