package com.clone.velog.web.domain.tag;

import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.dto.request.PostingRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tags {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="posting_id")
    private Posting posting;

    @Column
    private String tagName;

    public Tags(Posting posting, String name) {
        this.posting =posting;
        this.tagName =name;
    }

    //== 태그 생성 ==//
    public static List<Tags> createTag(Posting posting, PostingRequestDto postingRequestDto ) {
        List<String> tagNameList = postingRequestDto.getTags().getTagName();
        List<Tags> tagsList = new ArrayList<>();
        for(String tagName : tagNameList){
            Tags tags = new Tags(posting,tagName);
            tagsList.add(tags);
        }


        return tagsList;
    }


}
