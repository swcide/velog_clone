package com.clone.velog.web.dto.response;

import com.clone.velog.web.domain.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagNameAndCount {

    private Long tagId;
    private String tagName;
    private Posting posting;
    private Long count;
}