package com.clone.velog.web.dto.request;

import com.clone.velog.web.domain.tag.Tags;
import lombok.Getter;

import java.util.List;

@Getter
public class TagsRequestDto {
    private String tagName;
}
