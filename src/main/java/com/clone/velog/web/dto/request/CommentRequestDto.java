package com.clone.velog.web.dto.request;

import com.clone.velog.web.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    private Long memberId;
    private String content;
    private boolean status;

    public CommentRequestDto(Comment comment) {
        this.memberId =comment.getCommentId();
        this.content = comment.getContent();
        this.status = comment.getStatus();

    }
}