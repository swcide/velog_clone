package com.clone.velog.config.jwt.service;

import com.clone.velog.web.dto.request.MemberRequestDto;
import com.clone.velog.web.dto.response.MemberResponseDto;
import com.clone.velog.web.dto.response.TokenWithMemberResponseDto;
import com.clone.velog.config.jwt.dto.TokenDto;
import com.clone.velog.config.jwt.dto.TokenRequestDto;
import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.config.jwt.TokenProvider;
import com.clone.velog.web.domain.member.Member;
import com.clone.velog.config.jwt.domain.refresh.RefreshToken;
import com.clone.velog.web.domain.member.MemberRepository;
import com.clone.velog.config.jwt.domain.refresh.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원가입 예외처리
    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        // 이메일 , 닉네임 예외처리
        isEmailAndNickNameDuplicated(memberRequestDto);
        // 비밀번호 체크
//        memberRequestDto.pwdCheck();

        Member member = memberRequestDto.toMember(passwordEncoder);

        return MemberResponseDto.memberResponseDto(memberRepository.save(member));
    }

    @Transactional
    public TokenWithMemberResponseDto login(@Valid MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshKey(authentication.getName())
                .refreshValue(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        Member member = memberRepository.findByEmail(memberRequestDto.getEmail())
                .orElseThrow(() -> new ApiRequestException("멤버 못찾아~"));

        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        // 5. 토큰 발급
        return new TokenWithMemberResponseDto(memberResponseDto, tokenDto);
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new ApiRequestException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshKey(authentication.getName())
                .orElseThrow(() -> new ApiRequestException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getRefreshValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new ApiRequestException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급


        return tokenDto;
    }


    private void isEmailAndNickNameDuplicated(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new ApiRequestException("이미 가입되어 있는 유저입니다.");
        }

        if (memberRepository.existsByNickName(memberRequestDto.getNickName())) {
            throw new ApiRequestException("이미 사용 중인 별명입니다.");
        }
    }

}

