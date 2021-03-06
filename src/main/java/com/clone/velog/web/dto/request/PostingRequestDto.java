package com.clone.velog.web.dto.request;

import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;

import com.clone.velog.web.domain.tag.Tags;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
public class PostingRequestDto {

    @NotEmpty(message = "제목비었어~")
    private String title;
    private String content;
//    private String originalfilename;
    private Long memberId;
    private Long likeCount;
    private Tags tagList ;
    private String contentMd;
    private String previewText;
    private String originalFileName;

    @Getter
    public static class Tags {
        public List<String> stringTagName = new ArrayList<>();
    }
}
