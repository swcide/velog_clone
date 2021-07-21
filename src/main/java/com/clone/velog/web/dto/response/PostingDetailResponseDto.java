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
    private Long memberId;
    private String title;
    private String content;
    private Long likeCount;
    private String contentMd;
    private String previewText;
    private String originalFileName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentResponseDtoList;
    private List<TagResponseDto> tagResponseDtoList;

    public PostingDetailResponseDto(Posting posting, List<CommentResponseDto> commentResponseDtoList, List<TagResponseDto> tagResponseDto){
        this.postId = posting.getPostingId();
        this.memberId =posting.getMember().getMemberId();
        this.title = posting.getTitle();
        this.content = posting.getContent();
        this.likeCount = posting.getLikeCount();
        this.createdAt = posting.getCreatedAt();
        this.modifiedAt = posting.getModifiedAt();
        this.commentResponseDtoList = commentResponseDtoList;
        this.tagResponseDtoList = tagResponseDto;
        this.contentMd = posting.getContentMd();
        this.previewText = posting.getPreviewText();
        this.originalFileName = posting.getOriginalFileName();
    }
}
