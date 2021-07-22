package com.clone.velog.web.domain.tag;


import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.dto.request.PostingRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tags {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long tagId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="posting_id")
    private Posting posting;

    @Column
    private String tagName;





    public Tags(Posting posting) {
        this.posting = posting;

    }

    public Tags(String tagName) {
        this.tagName=tagName;
    }


    //== 태그 생성 ==//
    public static List<Tags> createTag(List<String> stringTagName) {
        List<Tags> tagsList = stringTagName
                .stream()
                .map(Tags::new)
                .collect(Collectors.toList());
        return tagsList;
    }


    public static List<Tags> updateTag(List<String> stringTagName) {
        List<Tags> tagsList = stringTagName.stream().map(Tags::new).collect(Collectors.toList());

        return tagsList;
    }
}
