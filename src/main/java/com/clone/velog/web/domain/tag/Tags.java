package com.clone.velog.web.domain.tag;

import com.clone.velog.web.domain.posting.Posting;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


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

    public Tags(String tagName) {
        this.tagName=tagName;
    }

    public void addPost(Posting posting){
        this.posting = posting;
    }

    //== 태그 생성 ==//
    public static List<Tags> createTag(List<String> stringTagName) {
        return stringTagName.stream()
                .map(Tags::new)
                .collect(Collectors.toList());
    }


    public static List<Tags> updateTag(List<String> stringTagName) {

        return stringTagName.stream()
                .map(Tags::new)
                .collect(Collectors.toList());
    }
}
