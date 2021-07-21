package com.clone.velog.web.domain.comment;

import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.common.Timestamped;
import com.clone.velog.web.dto.request.CommentRequestDto;
import com.clone.velog.exception.ApiRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long commentId;

    @Column(nullable = false)
    private String content;

    //  댓글 상테
    @Column(nullable = false)
    private Boolean status;

    // for counting
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // new comment
    public Comment(CommentRequestDto commentRequestDto, Posting posting, Member member){
        // 빈값 확인
        checkEmptyComment(commentRequestDto);
        this.content = commentRequestDto.getContent();
        this.posting = posting;
        this.member =member;
        this.status = true;
    }

    // comment 예외처리 - 변경가능영역
    public void deleteComment() {
        this.status = false;
    }



    // 댓글 수정 - 내용만 확인
    public void update(CommentRequestDto commentRequestDto,Member  member) {
        checkEmptyComment(commentRequestDto);
        checkMember(commentRequestDto,member);
        this.content = commentRequestDto.getContent();
    }




    public void checkEmptyComment(CommentRequestDto commentRequestDto){
        if(commentRequestDto.getContent().length() == 0 || commentRequestDto.getContent().isEmpty()){
            throw new ApiRequestException("내용을 입력해 주세요.");
        }
    }

    public void checkMember(CommentRequestDto commentRequestDto, Member member){
        if (commentRequestDto.getMemberId() != member.getMemberId()){
            throw new ApiRequestException("수정할 권한이 없습니다.");
        }
    }


}
