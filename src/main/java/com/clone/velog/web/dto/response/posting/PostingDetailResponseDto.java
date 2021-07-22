package com.clone.velog.web.dto.response.posting;


import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.dto.response.CommentResponseDto;
import com.clone.velog.web.dto.response.tag.TagResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private String imgUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentResponseDtoList;
    private List<TagResponseDto> tagResponseDtoList;

    public PostingDetailResponseDto(Posting posting, List<CommentResponseDto> commentResponseDtoList){
        this.postId = posting.getPostingId();
        this.memberId =posting.getMember().getMemberId();
        this.title = posting.getTitle();
        this.content = posting.getContent();
        this.likeCount = posting.getLikeCount();
        this.createdAt = posting.getCreatedAt();
        this.modifiedAt = posting.getModifiedAt();
        this.commentResponseDtoList = commentResponseDtoList;
        this.tagResponseDtoList = posting.getTags().stream().map(TagResponseDto::new).collect(Collectors.toList());
        this.contentMd = posting.getContentMd();
        this.previewText = posting.getPreviewText();
        this.imgUrl = posting.getImgUrl();
    }
}
