package com.clone.velog.web.dto.response;

import com.clone.velog.web.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private Long memberId;
    private String email;
    private String nickName;
    private String velogName;
    private String profileImg;
    private String github;
    private String comment;

    public MemberResponseDto(String email) {
        this.email = email;
    }

    public MemberResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.email =member.getEmail();
        this.nickName =member.getNickName();
        this.velogName =member.getVelogName();
        this.profileImg =member.getProfileImg();
        this.github =member.getGithub();
        this.comment = member.getComment();

    }


    public static MemberResponseDto memberResponseDto(Member member){

        return new MemberResponseDto(member.getEmail());
    }




}
