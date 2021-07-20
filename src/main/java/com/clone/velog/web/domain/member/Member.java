package com.clone.velog.web.domain.member;

import com.clone.velog.web.domain.common.Authority;
import com.clone.velog.web.domain.common.Timestamped;
import com.clone.velog.web.domain.posting.Posting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Member extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false,name="ZZZ")
    private String email;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String velogName;

    @Column
    private String mComment;

    @ColumnDefault("true")
    private boolean status;

    @Column
    private String profileImg;

    @Column
    private String github;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public Member(Long id, String email, String pwd, String nickname, String velogName, String mComment, boolean status, String profileImg, String github, Authority authority) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.velogName = velogName;
        this.mComment = mComment;
        this.status = status;
        this.profileImg = profileImg;
        this.github = github;
        this.authority = authority;

    }
}