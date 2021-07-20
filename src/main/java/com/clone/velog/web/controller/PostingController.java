package com.clone.velog.web.controller;

import com.clone.velog.web.dto.request.PostingRequestDto;
import com.clone.velog.web.dto.response.PostingAllResponseDto;
import com.clone.velog.web.dto.response.PostingDetailResponseDto;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posting")
@RestController
public class PostingController {

    private final PostingService postingService;

    @Autowired
    public PostingController(PostingService postingService){
        this.postingService = postingService;
    }


    //게시글 전체 (메인페이지)
    @GetMapping("")
    public Page<Posting> getAllPostings(){
        int page =1;
        int size =10;
        return postingService.getAllPosting(page,size);
    }

    // member에 따른 전체 게시물 목록
    @GetMapping("/{memberId}")
    public  Page<Posting> getMemberPostings(@PathVariable Long memberId){
        int page = 1;
        int size = 10;
        page = page-1;

        return postingService.getMemberPostings(memberId, page, size);
    }

    // member에 따른 상세 게시물
    @GetMapping("/detail/{postId}")
    public PostingDetailResponseDto getPostingDetail(@PathVariable Long postId){
        return postingService.getPostingDetail(postId);
    }

    // 게시물 등록
    @PostMapping("/write")
    public Long createPosting(@RequestBody PostingRequestDto postingRequestDto){

        return postingService.createPosting(postingRequestDto);
    }

    // 게시물 수정
    @PutMapping("/update/{postId}")
    public Long updatePosting(@PathVariable Long postId, @RequestBody PostingRequestDto postingRequestDto,@AuthenticationPrincipal UserDetails userDetails){
        // 현재 로그인한 유저 정보
        String memberEmail = userDetails.getUsername();
        System.out.println(memberEmail);
        return postingService.updatePosting(postId, postingRequestDto, memberEmail);
    }

    // 게시물 삭제
    @PutMapping("/delete/{postId}")
    public Long deletePosting(@PathVariable Long postId,@AuthenticationPrincipal UserDetails userDetails){
        String memberEmail = userDetails.getUsername();
        return postingService.deletePosting(postId,memberEmail);
    }

}