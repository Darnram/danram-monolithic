package com.danram.danram.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "feed_like")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FeedLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", columnDefinition = "int")
    private Long likeId;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "member_email", columnDefinition = "varchar", length = 50)
    private String memberEmail;

    @Column(name = "feed_id", columnDefinition = "int")
    private Long feedId;

    @Column(name = "created_at", columnDefinition = "date")
    @UpdateTimestamp
    private LocalDateTime createdAt;

    @Column(name = "deleted", columnDefinition = "tinyint")
    private Boolean deleted;
}
