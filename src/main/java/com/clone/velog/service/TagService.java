//package com.clone.velog.service;
//
//import com.clone.velog.exception.ApiRequestException;
//import com.clone.velog.web.domain.member.Member;
//import com.clone.velog.web.domain.member.MemberRepository;
//import com.clone.velog.web.domain.postTag.PostTagRepository;
//import com.clone.velog.web.domain.posting.Posting;
//import com.clone.velog.web.domain.posting.PostingRepository;
//import com.clone.velog.web.domain.tag.Tags;
//import com.clone.velog.web.domain.tag.TagsRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class TagService {
//
//    private final MemberRepository memberRepository;
//    private final TagsRepository tagsRepository;
//    private final PostingRepository postingRepository;
//
//
//
//    public List<Tags> createTags(Long postId, String username) {
//        Posting posting= getPosting(postId);
//        Tags tags = Tags.createTags(posting);
//    }
//
//    private Posting getPosting(Long postId) {
//
//        return postingRepository.findById(postId).orElseThrow(()-> new ApiRequestException("포스트를 찾을수가 없습니다."));
//
//    }
//
//
//    private Member getMember(String email) {
//        return memberRepository.findByEmail(email).orElseThrow(()-> new ApiRequestException("로그인된 유저가 없습니다."));
//    }
//
//}
