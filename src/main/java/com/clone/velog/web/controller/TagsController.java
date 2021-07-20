//package com.clone.velog.web.controller;
//
//import com.clone.velog.service.TagService;
//import com.clone.velog.web.domain.tag.Tags;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class TagsController {
//
//    private final TagService tagService;
//
//    @PostMapping("/tag}")
//    public List<Tags> createTags(@RequestBody  @AuthenticationPrincipal UserDetails userDetails) {
//        return tagService.createTags(postId, userDetails.getUsername());
//    }
//}
