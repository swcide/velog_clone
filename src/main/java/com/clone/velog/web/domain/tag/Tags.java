package com.clone.velog.web.domain.tag;

import com.clone.velog.web.domain.posting.Posting;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Tags {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="posting_id")
    private Posting posting;

    @Column
    private String tagName;




}
