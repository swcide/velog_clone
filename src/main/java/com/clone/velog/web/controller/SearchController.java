package com.clone.velog.web.controller;

import com.clone.velog.service.SearchService;
import com.clone.velog.web.dto.response.posting.PostingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchController {
    private final SearchService searchService;
//    private final
    @GetMapping("/api/search/{query}")
    public List<PostingResponseDto> search(@PathVariable String query,
                                           @RequestParam("page") int page,
                                           @RequestParam("size") int size){
        return searchService.search(query,page,size);
    }


    @GetMapping("/api/search/tag/{tagName}")
    public ResponseEntity<List<PostingResponseDto>> tagSearch(@PathVariable String tagName,
                                                              @RequestParam("page") int page,
                                                              @RequestParam("size") int size){


        return ResponseEntity.ok(searchService.tagSearch(tagName,page,size));

    }

}
