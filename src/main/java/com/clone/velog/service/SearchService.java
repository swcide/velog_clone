package com.clone.velog.service;

import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.domain.posting.PostingRepository;
import com.clone.velog.web.domain.tag.Tags;
import com.clone.velog.web.domain.tag.TagsRepository;
import com.clone.velog.web.dto.response.posting.PostingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final PostingRepository postingRepository;
    private final TagsRepository tagsRepository;

    public List<PostingResponseDto> search(String query,int page, int size) {
        Pageable pageable = PageRequest.of(page ,size);
        List<Posting> pagePaging = postingRepository.findByStatusTrueAndTitleContainsOrContentContainsOrderByCreatedAtDesc(query, query,pageable);

        return pagePaging.stream()
                .map(PostingResponseDto::new)
                .collect(toList());
    }

    /**
     * Todd
     * @param tagName
     * @param page
     * @param size
     * @return
     */
    public List<PostingResponseDto> tagSearch(String tagName,int page, int size) {
        List<Tags> tags = tagsRepository.findAllByTagName(tagName);
        List<Posting> post = tags.stream().map(Tags::getPosting).collect(toList());

        Pageable pageable = PageRequest.of(page, size);
        Page<Posting> PostPaging =  new PageImpl<>(post, pageable, post.size());


        return PostPaging
                .stream()
                .map(PostingResponseDto::new)
                .collect(toList());
    }
}
