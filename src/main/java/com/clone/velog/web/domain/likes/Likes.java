package com.clone.velog.web.domain.likes;

import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Likes{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    public Likes(Posting posting, Member member) {
        this.member=member;
        this.posting=posting;
    }
}
