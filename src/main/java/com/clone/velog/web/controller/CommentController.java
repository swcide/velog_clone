package com.clone.velog.web.controller;

import com.clone.velog.web.dto.request.CommentRequestDto;
import com.clone.velog.service.CommentService;
import com.clone.velog.web.dto.response.comment.CommentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){

        this.commentService = commentService;
    }


    @GetMapping("/{postId}")
    public List<CommentResponseDto> getAllComment(@PathVariable Long postId){
        return commentService.getAllComment(postId);
    }

    // 댓글 생성
    @PostMapping("/{postId}")
    public Long createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto){

        return commentService.createComment(commentRequestDto, postId);
    }

    // 댓글 수정
    @PutMapping("/update/{commentId}")
    public Long updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto,  @AuthenticationPrincipal UserDetails userDetails){
        String memberEmail = userDetails.getUsername();
        return commentService.updateComment(commentId,commentRequestDto,memberEmail);
    }

    // 댓글 삭제
    @PutMapping("/delete/{commentId}")
    public Long deleteComment(@PathVariable Long commentId, String memberEmail){
        return commentService.deleteComment(commentId,memberEmail);
    }

}

