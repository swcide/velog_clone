package com.clone.velog.web.domain.posting;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    List<Posting> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(String title, String content);

    // 내 게시물 조회
    @Query("select DISTINCT p from Posting p join fetch p.tags t join fetch p.member m  where p.member.memberId = :memberId")
    List<Posting> findAll(Long memberId);

    // 전체조회
    @Query("select p from Posting p join fetch p.member m ")
    List<Posting> findAllPaging(PageRequest createdAt);

    // 회원 게시물 조회
    @Query("select p from Posting p join fetch p.member m where p.member.memberId = :memberId  " )
    List<Posting> findAllMemberId(Long memberId, Pageable pageable);

//    List<Posting> findAll(Long memberId,PageRequest createdAt);




//    Page<Posting> findAllByMemberId(Long memberId, PageRequest pageRequest);
}
