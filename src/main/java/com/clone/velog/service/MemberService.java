package com.clone.velog.service;

import com.clone.velog.dto.MemberResponseDto;
import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.repository.MemberRepository;
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

}
