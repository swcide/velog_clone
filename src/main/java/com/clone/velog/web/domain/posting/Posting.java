package com.clone.velog.web.domain.posting;

import com.clone.velog.web.domain.common.Timestamped;
import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.dto.request.PostingRequestDto;
import com.clone.velog.exception.ApiRequestException;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class Posting extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;



    @Column
    private Long likeCount;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    @Column
//    private String originalFilename;
//
//    @Column
//    private String renameFilename;

    @Builder
    public Posting(String title, String content, Long likeCount, Member member) {
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.member = member;
//        this.originalFilename = originalFilename;
//        this.renameFilename = renameFilename;
        this.likeCount = 0L;
    }

    // new posting
//    public Posting(PostingRequestDto postingRequestDto) {
//        checkEmpty(postingRequestDto);
//        this.title = postingRequestDto.getTitle();
//        this.content = postingRequestDto.getContent();
////        this.originalFilename = postingRequestDto.getOriginalFilename();
//        this.member = postingRequestDto.getMember();
//        this.tag = postingRequestDto.getTag();
//    }

    public void deletePosting(Posting posting, Member member) {


    }

    public void updatePosting(PostingRequestDto postingRequestDto){
        checkEmpty(postingRequestDto);
        this.title = postingRequestDto.getTitle();
        this.content = postingRequestDto.getContent();
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
