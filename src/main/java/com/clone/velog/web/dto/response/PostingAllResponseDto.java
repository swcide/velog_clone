package com.clone.velog.web.dto.response;

import com.clone.velog.web.domain.comment.Comment;
import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.tag.Tags;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostingAllResponseDto {
    private List<PostingResponseDto> postingResponseDtos;
    private List<TagResponseDto> tagResponseDtoList;





}
