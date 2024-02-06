package com.danram.danram.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "feed_report")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FeedReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", columnDefinition = "int")
    private Long reportId;

    @Column(name = "content_type", columnDefinition = "int")
    private Long contentType;

    @Column(name = "description", columnDefinition = "varchar", length = 20)
    private String description;

    @Column(name = "report_type", columnDefinition = "int")
    private Long reportType;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "member_email", columnDefinition = "varchar", length = 50)
    private String memberEmail;

    @Column(name = "feed_owner", columnDefinition = "varchar", length = 50)
    private String feedOwner;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDateTime createdAt;
}
