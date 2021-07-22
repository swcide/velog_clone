package com.clone.velog.web.domain.posting;

import com.clone.velog.web.dto.response.PostingResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    Page<Posting> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(String title, String content, PageRequest createdAt);


//    Page<Posting> findAllByMemberId(Long memberId, PageRequest pageRequest);
}
