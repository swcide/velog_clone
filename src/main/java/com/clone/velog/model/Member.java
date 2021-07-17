package com.clone.velog.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Member extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long mid;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private String mname;

    @Column(nullable = false)
    private String velogname;

    @Column(nullable = false)
    private String mcomment;

    @Column(nullable = false)
    private String mstatus;

    @Column(nullable = false)
    private String profileimg;

    @Column(nullable = false)
    private String github;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String email, String pwd, String mname, String velogname, String mcomment, String mstatus, String profileimg, String github, Authority authority) {
        this.email = email;
        this.pwd = pwd;
        this.mname = mname;
        this.velogname = velogname;
        this.mcomment = mcomment;
        this.mstatus = mstatus;
        this.profileimg = profileimg;
        this.github = github;
        this.authority = authority;

    }
}