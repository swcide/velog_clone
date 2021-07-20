package com.clone.velog.web.domain.posting;

import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.dto.response.PostingAllResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    Page<Posting> findAllByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);
//    Page<Posting> findAllByMember(Long mId, Pageable pageable);
//
//    @Query("SELECT p from Posting p where p.member.id=:memberId and p.id>0 order by  p.createdAt DESC ")
//    Page<PostingAllResponseDto> findAllByMemberId(Long memberId, Pageable pageable);

    List<Posting> findAllByMemberId(Long memberId);
}
