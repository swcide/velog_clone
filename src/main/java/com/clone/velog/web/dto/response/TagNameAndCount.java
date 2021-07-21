package com.clone.velog.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagNameAndCount {
    String tagName;
    Long count;
}
