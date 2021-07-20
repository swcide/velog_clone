package com.clone.velog.web.dto.request;

import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.tag.Tags;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostingRequestDto {

    @NotEmpty(message = "제목비었어~")
    private String title;
    private String content;
//    private String originalfilename;
    private Long memberId;
    private List<Tags> tags;

    public Posting createPost(Member member) {
        return Posting.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }


}
