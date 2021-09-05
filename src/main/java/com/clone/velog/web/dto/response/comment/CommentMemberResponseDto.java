package com.clone.velog.web.dto.response.comment;

import com.clone.velog.web.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentMemberResponseDto {
    private Long memberId;
    private String profileImg;
    private String nickName;

    public CommentMemberResponseDto(Member member){
        this.memberId = member.getMemberId();
        this.profileImg = member.getProfileImg();
        this.nickName = member.getNickName();
    }
}
