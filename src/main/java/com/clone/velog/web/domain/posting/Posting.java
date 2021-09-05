package com.clone.velog.web.domain.posting;

import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.web.domain.common.Timestamped;
import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.tag.Tags;
import com.clone.velog.web.dto.request.PostingRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class Posting extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
//    @Column(name="posting_id")
    private Long postingId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private Long likeCount;

    @Column
    private boolean status;

    @Column
    private String contentMd;

    @Column
    private String previewText;


    //img url
    @Column
    private String imgUrl;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(
            mappedBy = "posting",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Tags> tags = new ArrayList<>();

    public Posting(String title, String content, String contentMd, String previewText, Member member) {
        this.title = title;
        this. content = content;
        this.contentMd =contentMd;
        this.previewText = previewText;
        this. member = member;
    }


    public void addTags(Tags tags) {
        this.tags.add(tags);
        tags.addPost(this);
    }

    public static Posting createPosting(Member member,PostingRequestDto postingRequestDto,List<Tags> tags ){
        Posting posting =new Posting(
                postingRequestDto.getTitle(),
                postingRequestDto.getContent(),
                postingRequestDto.getContentMd(),
                postingRequestDto.getPreviewText(),
                member
        );

        for (Tags tag : tags) {
            posting.addTags(tag);
        }

        return posting;
    }

    // 업데이트
    public void updatePosting(PostingRequestDto postingRequestDto, List<Tags> tags){
        checkEmpty(postingRequestDto);
        this.title = postingRequestDto.getTitle();
        this.content = postingRequestDto.getContent();
        for (Tags tag : tags) {
            this.addTags(tag);
        }
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
