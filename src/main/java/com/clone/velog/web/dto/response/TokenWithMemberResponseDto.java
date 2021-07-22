package com.clone.velog.web.dto.response;

import com.clone.velog.config.jwt.dto.TokenDto;
import com.clone.velog.web.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenWithMemberResponseDto {
    MemberResponseDto member;
    TokenDto tokenDto;

    public TokenWithMemberResponseDto(MemberResponseDto member, TokenDto tokenDto) {
        this.member = member;
        this.tokenDto = tokenDto;
    }
}
