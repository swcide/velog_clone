package com.clone.velog.service;

import com.clone.velog.exception.ApiRequestException;
import com.clone.velog.web.domain.likes.Likes;
import com.clone.velog.web.domain.likes.LikesRepository;
import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.member.MemberRepository;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.posting.PostingRepository;
import com.clone.velog.web.dto.response.posting.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {


    private final LikesRepository likesRepository;
    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public String Likes(LikeResponseDto likeResponseDto) {
        Posting posting = getPost(likeResponseDto);
        Member member = getMember(likeResponseDto);

        Likes optionalLike = likesRepository.findByMemberAndPosting(member,posting);

        if(optionalLike==null) {
            Likes likes = new Likes(posting, member);
            likesRepository.save(likes);
        }else{
            likesRepository.deleteById(optionalLike.getLikeId());
        }

        return "like";
    }


    private Member getMember(LikeResponseDto likeResponseDto) {
        return memberRepository.findById(likeResponseDto.getMemberId()).orElseThrow(() -> new ApiRequestException("멤버 정보가 없습니다"));
    }

    private Posting getPost(LikeResponseDto likeResponseDto) {
        return postingRepository.findById(likeResponseDto.getPostingId()).orElseThrow(() -> new ApiRequestException("게시글 정보가 없습니다"));
    }
}
