package com.clone.velog.web.domain.likes;


import com.clone.velog.web.domain.member.Member;
import com.clone.velog.web.domain.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findByMemberAndPosting(Member member, Posting posting);
}
