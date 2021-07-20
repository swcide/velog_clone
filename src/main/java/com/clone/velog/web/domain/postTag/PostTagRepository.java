package com.clone.velog.web.domain.postTag;


import com.clone.velog.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long>{

//    List<PostTag> findAll(Member member);
}
