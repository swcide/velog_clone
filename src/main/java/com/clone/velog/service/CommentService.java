package com.clone.velog.service;

import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.member.MemberRepository;
import com.clone.velog.web.dto.request.CommentRequestDto;
import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.web.domain.comment.Comment;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.comment.CommentRepository;
import com.clone.velog.web.domain.posting.PostingRepository;
import com.clone.velog.web.dto.response.comment.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;





    public List<CommentResponseDto> getAllComment(Long postId) {
        Posting posting = getPosting(postId);
        List<Comment> comments = commentRepository.findAllByPostingOrderByCreatedAtDesc(posting);
        List<CommentResponseDto> commentResponseDtoList =comments
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return commentResponseDtoList;
    }

    // 댓글 생성
    @Transactional
    public Long createComment(CommentRequestDto commentRequestDto, Long postId) {
        Posting posting = getPosting(postId);

        Member member = getMemberById(commentRequestDto);

        Comment comment = new Comment(commentRequestDto, posting, member);
        return commentRepository.save(comment).getCommentId();
    }



    // 댓글 수정
    @Transactional
    public Long updateComment(Long commentId, CommentRequestDto commentRequestDto,String memberEmail) {
        Comment comment = getComment(commentId);
        Member member = getMemberByEmail(memberEmail);

        comment.update(commentRequestDto,member);
        return comment.getCommentId();
    }

    // 댓글 삭제
    @Transactional
    public Long deleteComment(Long commentId,String memberEmail) {
        Comment comment = getComment(commentId);
        Member member = getMemberByEmail(memberEmail);
        if(!comment.getMember().getMemberId().equals(member.getMemberId())) {
            throw new ApiRequestException("수정할 권한이 없습니다.");
        }

        comment.deleteComment();
        return comment.getCommentId();
    }

    private Member getMemberById(CommentRequestDto commentRequestDto) {
        Member member = memberRepository.findById(commentRequestDto.getMemberId())
                .orElseThrow(()-> new ApiRequestException("멤버가 존재하지 않습니다."));
        return member;
    }
    private Posting getPosting(Long postId) {
        return postingRepository.findById(postId)
                .orElseThrow(()-> new ApiRequestException("해당 게시글이 존재하지 않습니다.")                );
    }
    private Member getMemberByEmail(String memberEmail) {
        return memberRepository.findByEmail(memberEmail).orElseThrow(()->new ApiRequestException("멤버가 존재하지 않습니다."));
    }
    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()->new ApiRequestException("해당 댓글이 존재하지 않습니다."));
    }




//    public
}
