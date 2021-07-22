package com.clone.velog.web.controller;

import com.clone.velog.service.PostingService;
import com.clone.velog.web.dto.request.PostingRequestDto;
import com.clone.velog.web.dto.response.PostingDetailResponseDto;
import com.clone.velog.web.dto.response.PostingResponseDto;
import com.clone.velog.web.dto.response.PostingAllByMemberResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<PostingResponseDto> getAllPostings(){
        int page =1;
        int size =5;
        return postingService.getAllPosting(page,size);
    }

    // member에 따른 전체 게시물 목록
    @GetMapping("/{memberId}")
    public PostingAllByMemberResponseDto getMemberPostings(@PathVariable Long memberId){
        int page = 0;
        int size = 10;

        return postingService.getMemberPostings(memberId, page, size);
    }
//
    // member에 따른 상세 게시물
    @GetMapping("/detail/{postId}")
    public PostingDetailResponseDto getPostingDetail(@PathVariable Long postId){
        return postingService.getPostingDetail(postId);
    }
//
    // 게시물 등록
    @PostMapping("/write")
    public Long createPosting(@RequestBody PostingRequestDto postingRequestDto){

//        System.out.println(postingRequestDto.getTagList().getStringTagName());
        return postingService.createPosting(postingRequestDto);
    }
//
//    // 게시물 수정
//    @PutMapping("/update/{postId}")
//    public Long updatePosting(@PathVariable Long postId, @RequestBody PostingRequestDto postingRequestDto,@AuthenticationPrincipal UserDetails userDetails){
//        // 현재 로그인한 유저 정보
//        String memberEmail = userDetails.getUsername();
//        System.out.println(memberEmail);
//        return postingService.updatePosting(postId, postingRequestDto, memberEmail);
//    }
//
//    // 게시물 삭제
//    @PutMapping("/delete/{postId}")
//    public Long deletePosting(@PathVariable Long postId,@AuthenticationPrincipal UserDetails userDetails){
//        String memberEmail = userDetails.getUsername();
//
//        System.out.println(memberEmail);
//        return postingService.deletePosting(postId,memberEmail);
//    }

}
