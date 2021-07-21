package com.clone.velog.web.dto.request;

import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;

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
    private Tags tags;

    public Posting createPost(Member member) {
        return Posting.builder()
                .title(title)
                .content(content)
                .likeCount(likeCount)
                .member(member)
                .build();
    }

    @Getter
    public static class Tags {
        public List<String> tagName = new ArrayList<>();
    }
}
