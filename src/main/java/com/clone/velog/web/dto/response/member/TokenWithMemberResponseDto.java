package com.clone.velog.web.dto.response.member;

import com.clone.velog.config.jwt.dto.TokenDto;
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
