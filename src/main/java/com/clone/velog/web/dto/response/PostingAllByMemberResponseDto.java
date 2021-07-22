package com.clone.velog.web.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostingAllByMemberResponseDto {
    private List<PostingResponseDto> postingResponseDto;
    private List<TagResponseDto> tagList;
    private MemberResponseDto memberResponseDto;


    public PostingAllByMemberResponseDto(List<PostingResponseDto> postingResponseDto, List<TagResponseDto> tagResponseDto, MemberResponseDto memberResponseDto) {
        this.postingResponseDto = postingResponseDto;
        this.tagList = tagResponseDto;
        this.memberResponseDto = memberResponseDto;
    }
}
