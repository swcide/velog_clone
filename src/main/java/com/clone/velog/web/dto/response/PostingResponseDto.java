package com.clone.velog.web.dto.response;

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
public class PostingResponseDto {

    private Long postId;
    private String title;
    private String content;
    private Member member;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public PostingResponseDto(Posting posting) {
        this.postId = posting.getId();
        this.title = posting.getTitle();
        this.content = posting.getContent();
        this.member =posting.getMember();
        this.createdAt = posting.getCreatedAt();
        this.modifiedAt = posting.getModifiedAt();
    }
}