package com.clone.velog.web.domain.posting;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {

    List<Posting> findByStatusTrueAndTitleContainsOrContentContainsOrderByCreatedAtDesc(String title, String content, Pageable pageable);


    // 내 게시물 조회
    @Query("select DISTINCT p from Posting p join fetch p.tags t join fetch p.member m  where p.member.memberId = :memberId and p.status = true")
    List<Posting> findAll(Long memberId);

    // 전체조회
    @Query("select p from Posting p join fetch p.member m where p.status = true")
    List<Posting> findAllPaging(PageRequest createdAt);

    // 회원 게시물 조회
    @Query("select p from Posting p join fetch p.member m where p.member.memberId = :memberId  and p.status = true" )
    List<Posting> findAllMemberId(Long memberId, Pageable pageable);



//    List<Posting> findAll(Long memberId,PageRequest createdAt);




//    Page<Posting> findAllByMemberId(Long memberId, PageRequest pageRequest);
}
