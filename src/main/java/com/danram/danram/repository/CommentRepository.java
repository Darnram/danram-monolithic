package com.danram.danram.repository;

import com.danram.danram.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByFeedIdAndDeletedFalse(Long feedId, Sort sort);
    public Optional<Comment> findByCommentIdAndMemberId(Long commentId, Long memberId);
}
