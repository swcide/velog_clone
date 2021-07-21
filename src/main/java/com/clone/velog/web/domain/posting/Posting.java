package com.clone.velog.web.domain.posting;

import com.clone.velog.web.domain.common.Timestamped;
import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.dto.request.PostingRequestDto;
import com.clone.velog.exception.ApiRequestException;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class Posting extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long postingId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private Long likeCount;

    @Column
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



    @Builder
    public Posting(String title, String content, Long likeCount, Member member) {
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.member = member;
        this.status = true;
        this.likeCount = 0L;
    }

    // 업데이트
    public void updatePosting(PostingRequestDto postingRequestDto){
        checkEmpty(postingRequestDto);
        this.title = postingRequestDto.getTitle();
        this.content = postingRequestDto.getContent();
    }
    //삭제
    public void deletePosting() {
        this.status = false;
    }


    public void checkEmpty(PostingRequestDto postingRequestDto){
        if(postingRequestDto.getTitle() == null || postingRequestDto.getTitle().isEmpty()){
            throw new ApiRequestException("제목은 필수 입력값입니다.");
        }
        if(postingRequestDto.getContent() == null || postingRequestDto.getContent().isEmpty()){
            throw new ApiRequestException("내용은 필수 입력값입니다.");
        }
    }


}
