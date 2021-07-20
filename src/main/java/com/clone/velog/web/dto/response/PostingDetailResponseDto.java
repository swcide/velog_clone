package com.clone.velog.web.dto.response;


import com.clone.velog.web.domain.posting.Posting;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostingDetailResponseDto {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentResponseDtoList;


    public PostingDetailResponseDto(Posting posting, List<CommentResponseDto> commentResponseDtoList){
        this.postId = posting.getId();
        this.title = posting.getTitle();
        this.content = posting.getContent();
        this.createdAt = posting.getCreatedAt();
        this.modifiedAt = posting.getModifiedAt();
        this.commentResponseDtoList = commentResponseDtoList;
    }
}
