package com.clone.velog.web.dto.response;

import com.clone.velog.web.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long memberId;
    private Long postingId;
    private String content;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.memberId=comment.getMember().getMemberId();
        this.postingId = comment.getPosting().getPostingId();
        this.content = comment.getContent();
        this.status = comment.getStatus();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();

    }
}
