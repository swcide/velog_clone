package com.clone.velog.web.controller;

import com.clone.velog.service.LikesService;
import com.clone.velog.web.domain.likes.LikesRepository;
import com.clone.velog.web.dto.response.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;
    private final LikesRepository likesRepository;


    @PostMapping("/api/like")
    public ResponseEntity<Void> likes(@RequestBody LikeResponseDto likeResponseDto){
        likesService.Likes(likeResponseDto);
        return ResponseEntity.ok().build();
    }


}
