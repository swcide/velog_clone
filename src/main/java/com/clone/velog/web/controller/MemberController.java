package com.clone.velog.web.controller;

import com.clone.velog.web.dto.request.MemberRequestDto;
import com.clone.velog.web.dto.response.MemberResponseDto;
import com.clone.velog.web.dto.response.TokenWithMemberResponseDto;
import com.clone.velog.config.jwt.dto.TokenDto;
import com.clone.velog.config.jwt.dto.TokenRequestDto;
import com.clone.velog.config.jwt.service.AuthService;
import com.clone.velog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    // 유저정보
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        return ResponseEntity.ok(memberService.getMyInfo());
    }

    //이메일 validate
    @GetMapping("/register/{email}")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberInfo(email));
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenWithMemberResponseDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    // 탈퇴
    @PutMapping("/withdrawal")
    public ResponseEntity<Void> withdrawal(@AuthenticationPrincipal UserDetails userDetails) {
        memberService.withdrawal(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    // 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

}
