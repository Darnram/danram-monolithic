package com.danram.danram.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", columnDefinition = "int")
    private Long imageId;

    @Column(name = "image_url", columnDefinition = "text")
    private String imageUrl;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "member_email", columnDefinition = "varchar", length = 50)
    private String memberEmail;

    @Column(name = "description", columnDefinition = "varchar", length = 50)
    private String description;

    @Column(name = "created_at", columnDefinition = "datetime")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
