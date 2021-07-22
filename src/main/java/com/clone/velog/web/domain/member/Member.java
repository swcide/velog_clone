package com.clone.velog.web.domain.member;

import com.clone.velog.web.domain.common.Authority;
import com.clone.velog.web.domain.common.Timestamped;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.dto.request.MemberRequestDto;
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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long memberId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private String nickName;

    @Column
    private String velogName;

    @Column
    private String comment;

    @ColumnDefault("1")
    private boolean status;

    @Column
    private String profileImg;

    @Column
    private String github;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public Member(Long id, String email, String pwd, String nickName, String velogName, String comment, boolean status, String profileImg, String github, Authority authority) {
        this.memberId = id;
        this.email = email;
        this.pwd = pwd;
        this.nickName = nickName;
        this.velogName = velogName;
        this.comment = comment;
        this.status = status;
        this.profileImg = profileImg;
        this.github = github;
        this.authority = authority;

    }

    public void updateMember(MemberRequestDto memberRequestDto) {
        this.nickName = memberRequestDto.getNickName();
        this.comment = memberRequestDto.getComment();
        this.github = memberRequestDto.getGithub();
        this.profileImg = memberRequestDto.getProfileImg();
        this.pwd =memberRequestDto.getProfileImg();
    }

    // 탈퇴
    public void memberStatusDelete(){
        this.status = false;
    }
}