package com.clone.velog.web.domain.postTag;

import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.tag.Tags;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class PostTag {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="posting_id")
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


}
