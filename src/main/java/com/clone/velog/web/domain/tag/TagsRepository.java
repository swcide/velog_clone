package com.clone.velog.web.domain.tag;

import com.clone.velog.web.domain.posting.Posting;
import com.clone.velog.web.dto.response.tag.TagNameAndCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TagsRepository extends JpaRepository<Tags, Long> {





    // 유저 개인페이지 태그 정보
    @Query("select new com.clone.velog.web.dto.response.tag.TagNameAndCount(t.tagId, t.tagName,t.posting ,count(t.tagName)) from Tags t where t.posting.member.memberId =:memberId  group by t.tagName order by count(t.tagName) desc ")
    List<TagNameAndCount> findAll(Long memberId);


    // 중복 태그
    Tags findTagsByTagNameAndPosting(String tagName, Posting posting);


    // 디테일 , 업데이트, 딜리트 태그 정보
    List<Tags> findAllByPosting(Posting posting);

    // 태그 검색
    List<Tags> findAllByTagName(String tagName);


    //수정
    void deleteByPosting(Posting posting);

}
