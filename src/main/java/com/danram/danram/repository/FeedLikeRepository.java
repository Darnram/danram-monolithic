package com.danram.danram.repository;

import com.danram.danram.domain.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
    public List<FeedLike> findByFeedIdAndDeletedFalse(Long feedId);
    public Optional<FeedLike> findByFeedIdAndMemberId(Long feedId, Long memberId);
}
