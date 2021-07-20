package com.clone.velog.web.domain.comment;

import com.clone.velog.web.domain.comment.Comment;
import com.clone.velog.web.domain.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostingOrderByCreatedAtDesc(Posting posting);
    // 삭제할 posting에 달린 comment 우선 삭제
    void deleteAllByPosting(Posting posting);
}
