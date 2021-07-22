package com.clone.velog.service;

import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.dto.request.MemberRequestDto;
import com.clone.velog.web.dto.response.MemberResponseDto;
import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.web.domain.member.MemberRepository;
import com.clone.velog.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;




    @Transactional(readOnly = true)
    public MemberResponseDto getMemberInfo(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponseDto::memberResponseDto)
                .orElseThrow(() -> new ApiRequestException("유저 정보가 없습니다."));
    }

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::memberResponseDto)
                .orElseThrow(() -> new ApiRequestException("로그인 유저 정보가 없습니다."));
    }

    // 회원 프로필 조회
    @Transactional
    public MemberResponseDto getMemberDetail(String email) {
        Member member = EmailCheck(email);
        return new MemberResponseDto(member);
    }

    // 회원 프로필 수정
    @Transactional
    public Long updateMemberDetail(MemberRequestDto memberRequestDto, String email) {
        Member member = EmailCheck(email);
        member.updateMember(memberRequestDto);
        return member.getMemberId();
    }

    // 탈퇴
    @Transactional
    public void withdrawal(String username) {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(()-> new ApiRequestException("유저 정보가 없습니다."));
        member.memberStatusDelete();
    }

    // 유저를 email로 존재 여부 체크한 후 member로 반환하는 메서드
    public Member EmailCheck(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("유저 정보가 없습니다."));
        return member;
    }
}
