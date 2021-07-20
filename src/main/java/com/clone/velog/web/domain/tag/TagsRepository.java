package com.clone.velog.web.domain.tag;

import com.clone.velog.web.domain.posting.Posting;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TagsRepository extends JpaRepository<Tags, Long> {


//    list<Tags> findAll();

    List<Tags> findAllByPosting_Id(Long id);

    List<Tags> findAll();
    @Query("select t from Tags t where t.posting.member.id =:memberId")
    List<Tags> findAll(Long memberId);
}
