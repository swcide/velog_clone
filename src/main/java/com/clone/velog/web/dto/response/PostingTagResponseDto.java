package com.clone.velog.web.dto.response;

import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.tag.Tags;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostingTagResponseDto {

    private Long postId;
    private String title;
    private String content;
    private Long memberId;
    private Long likeCount;
    private String contentMd;
    private String previewText;
    private String imgUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<Tags> tags;


    public PostingTagResponseDto(Posting posting) {
        this.postId = posting.getPostingId();
        this.title = posting.getTitle();
        this.content = posting.getContent();
        this.memberId =posting.getMember().getMemberId();
        this.likeCount =posting.getLikeCount();
        this.createdAt = posting.getCreatedAt();
        this.modifiedAt = posting.getModifiedAt();
        this.contentMd = posting.getContentMd();
        this.previewText = posting.getPreviewText();
        this.imgUrl = posting.getImgUrl();
        this.tags = posting.getTags();
    }
}
