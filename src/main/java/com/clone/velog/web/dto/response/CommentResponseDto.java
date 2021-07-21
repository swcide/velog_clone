package com.clone.velog.web.dto.response;

import com.clone.velog.web.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long memberId;
    private String content;
    private Boolean status;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.memberId=comment.getMember().getMemberId();
        this.content = comment.getContent();
        this.status = comment.getStatus();
    }
}
