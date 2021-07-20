package com.clone.velog.service;

import com.clone.velog.web.domain.tag.Tags;
import com.clone.velog.web.domain.tag.TagsRepository;
import com.clone.velog.web.dto.response.CommentResponseDto;
import com.clone.velog.web.dto.request.PostingRequestDto;
import com.clone.velog.web.dto.response.PostingAllResponseDto;
import com.clone.velog.web.dto.response.PostingDetailResponseDto;
import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.web.domain.comment.Comment;
import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.comment.CommentRepository;
import com.clone.velog.web.domain.member.MemberRepository;
import com.clone.velog.web.domain.posting.PostingRepository;
import com.clone.velog.web.dto.response.PostingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Posting> getAllPosting(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        return postingRepository.findAll(pageable);
    }

    // 내 게시물 전체 목록
    public  Page<Posting> getMemberPostings(Long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Member member = getMemberById(memberId);
        List<Tags> tagsList2 =tagsRepository.findAll(memberId);
        Page<Posting> postings = postingRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId, pageable);

        Pageable page2 = postings.getPageable();
//        int totalSize =

        List<PostingResponseDto> postingResponseDto = postings
                .stream()
                .map(PostingResponseDto::new)
                .collect(Collectors.toList());

        PostingAllResponseDto aas = new PostingAllResponseDto();
//
//
//        page2.getPageSize();
//        postings.getTotalElements();
//        postings.get



//        Page<PostingAllResponseDto> postingAllResponseDto = postingRepository.findAllByMemberId(memberId, pageable);
//        List<Posting> postings = postingRepository.findAllByMemberId(memberId);





//        List<TagResponseDto> tagResponseDtoList = tagsList.stream().map(tag->new TagResponseDto()).collect(Collectors.toList());
//
//        List<PostingAllResponseDto> postingAllResponseDto =
//                postings
//                .stream()
//                        .map()
//                .map(posting -> new PostingAllResponseDto(posting,tagResponseDtoList))
//                .collect(Collectors.toList());
//
//
        return postings;
//        return  postingRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId, pageable);

    }

    // 게시물 상세
    public PostingDetailResponseDto getPostingDetail(Long postId) {
        Posting posting = getPost(postId);

        List<Comment> commentList = commentRepository.findAllByPostingOrderByCreatedAtDesc(posting);
        System.out.println(commentList);
        List<CommentResponseDto> commentResponseDtoList = commentList.stream().map(CommentResponseDto::new).collect(Collectors.toList());
        System.out.println(commentList);

        return new PostingDetailResponseDto(posting, commentResponseDtoList);
    }

    // 게시물 등록
    @Transactional
    public Long createPosting(PostingRequestDto postingRequestDto) {
//        Posting posting = new Posting(postingRequestDto);
        List<Tags> tagsList = postingRequestDto.getTags();





        Member member = getMemberById(postingRequestDto.getMemberId());

        Posting posting = postingRequestDto.createPost(member);
        postingRepository.save(posting);
        return posting.getId();
    }


    // 게시물 수정
    @Transactional
    public Long updatePosting(Long postId, PostingRequestDto postingRequestDto, String memberEmail) {
        // 게시물 존재 여부 확인
        Posting posting = getPost(postId);
        Member member = getMemberByEmail(memberEmail);
        // 게시자 확인
        if(!postingRequestDto.getMemberId().equals(member.getId())){
            throw new ApiRequestException("해당 게시물에 대한 수정 권한이 없습니다.");
        }

        posting.updatePosting(postingRequestDto);
        return posting.getId();
    }



    // 게시물 삭제
    @Transactional
    public Long deletePosting(Long postId, String memberEmail){
        Posting posting = getPost(postId);
        Member member = getMemberByEmail(memberEmail);

        if(!posting.getMember().getId().equals(member.getId())){
            throw new ApiRequestException("해당 게시물에 대한 수정 권한이 없습니다.");
        }
        posting.deletePosting(posting,member);

        return getPost(postId).getId();
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

}
