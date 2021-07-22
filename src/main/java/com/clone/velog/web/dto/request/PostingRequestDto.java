package com.clone.velog.web.dto.request;

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
