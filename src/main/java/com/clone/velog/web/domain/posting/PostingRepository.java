package com.clone.velog.web.domain.posting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting, Long> {


//    Page<Posting> findAllByMemberId(Long memberId, PageRequest pageRequest);
}
