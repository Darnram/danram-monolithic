package com.danram.danram.repository;

import com.danram.danram.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByFeedIdAndDeletedFalse(Long feedId, Sort sort);
}
