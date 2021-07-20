package com.clone.velog.web.dto.response;


import lombok.Getter;

@Getter
public class TagResponseDto {


    private Long id;
    private Long postId;
    private String tagName;
}
