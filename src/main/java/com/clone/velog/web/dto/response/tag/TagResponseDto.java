package com.clone.velog.web.dto.response.tag;


import com.clone.velog.web.domain.tag.Tags;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDto {


    private Long tagId;
    private Long postId;
    private String tagName;
    private Long count;

    public TagResponseDto(Tags tags) {
        this.tagId =tags.getTagId();
        this.postId = tags.getPosting().getPostingId();
        this.tagName =tags.getTagName();
    }

    public TagResponseDto(TagNameAndCount tag) {
        this.tagId = tag.getTagId();
        this.tagName =tag.getTagName();
        this.postId =tag.getPosting().getPostingId();
        this.count =tag.getCount();
    }
}
