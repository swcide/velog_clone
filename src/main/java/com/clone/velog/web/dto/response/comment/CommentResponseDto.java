package com.clone.velog.web.dto.response.comment;

import com.clone.velog.web.domain.comment.Comment;
import com.clone.velog.web.dto.response.comment.CommentMemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto{
    private Long commentId;
    private Long postingId;
    private String content;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private CommentMemberResponseDto commentUserResponseDto;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.postingId = comment.getPosting().getPostingId();
        this.content = comment.getContent();
        this.status = comment.getStatus();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.commentUserResponseDto = new CommentMemberResponseDto(comment.getMember());
    }
}
