package com.clone.velog.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostingAllResponseDto {
    private List<PostingResponseDto> postingResponseDto;
    private List<TagResponseDto> tagList;
    private List<TagNameAndCount> tagNameAndCounts;
    private MemberResponseDto memberResponseDto;




    public PostingAllResponseDto(List<PostingResponseDto> postingResponseDto, List<TagNameAndCount> tagResponseDto2,MemberResponseDto memberResponseDto) {
        this.postingResponseDto = postingResponseDto;
        this.tagNameAndCounts =tagResponseDto2;
        this.memberResponseDto =memberResponseDto;
    }

}
