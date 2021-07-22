package com.clone.velog.web.dto.response;

import com.clone.velog.web.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostingMemberResponseDto {
    private Long memberId;
    private String profileImg;
    private String nickName;

    public PostingMemberResponseDto(Member member){
        this.memberId = member.getMemberId();
        this.profileImg = member.getProfileImg();
        this.nickName = member.getNickName();
    }
}
