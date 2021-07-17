package com.clone.velog.service;

import com.clone.velog.dto.MemberRequestDto;
import com.clone.velog.dto.MemberResponseDto;
import com.clone.velog.dto.TokenDto;
import com.clone.velog.dto.TokenRequestDto;
import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.jwt.TokenProvider;
import com.clone.velog.model.Member;
import com.clone.velog.model.RefreshToken;
import com.clone.velog.repository.MemberRepository;
import com.clone.velog.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // 이메일 예외처리
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new ApiRequestException("이미 가입되어 있는 유저입니다.");
        }
        else if(memberRequestDto.getEmail() == null){
            throw new ApiRequestException("이메일은 필수 입력값입니다.");
        }
        else if(!memberRequestDto.getEmail().matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\\\.[a-z]+$")){
            throw new ApiRequestException("올바른 이메일 형식이 아닙니다.");
        }
        // 닉네임 예외처리
        if(memberRepository.existsByMname(memberRequestDto.getMname())){
            throw new ApiRequestException("이미 사용 중인 별명입니다.");
        }
        else if(memberRequestDto.getMname() == null){
            throw new ApiRequestException("별명은 필수 입력값입니다.");
        }
        // 비밀번호 예외처리
        if(memberRequestDto.getPwd() == null){
            throw new ApiRequestException("비밀번호는 필수 입력값입니다.");
        }
        else if(!memberRequestDto.getPwd().matches("^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$")){
            throw new ApiRequestException("비밀번호는 숫자, 영문, 특수문자 각 1자리 이상 사용하여, 8자 이상 16자 이하로 설정하여야 합니다.");
        }


        Member member = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.memberResponseDto(memberRepository.save(member));
    }

    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
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
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new ApiRequestException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
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
}
