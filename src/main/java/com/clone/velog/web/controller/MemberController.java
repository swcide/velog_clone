package com.clone.velog.web.controller;

import com.clone.velog.web.domain.member.Member;
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

import javax.validation.Valid;

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
    public ResponseEntity<MemberResponseDto> signup(@Valid @RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenWithMemberResponseDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    // 회원 프로필 조회
    @GetMapping("/setting/{memberId}")
    public ResponseEntity<MemberResponseDto> getMemberDetail(@PathVariable Long memberId, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        memberService.getMemberDetail(memberId, email);
        return ResponseEntity.ok().build();
    }

    // 회원 프로필 수정
    @PutMapping("/setting/{memberId}")
    public ResponseEntity<Void> updateMemberDetail (@PathVariable Long memberId,
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody MemberRequestDto memberRequestDto){
        String email = userDetails.getUsername();
        memberService.updateMemberDetail(memberRequestDto,email, memberId);
        return ResponseEntity.ok().build();
    }

    // 탈퇴
    @PutMapping("/withdrawal")
    public ResponseEntity<Void> withdrawal(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        memberService.withdrawal(email);
        return ResponseEntity.ok().build();
    }

    // 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

}
