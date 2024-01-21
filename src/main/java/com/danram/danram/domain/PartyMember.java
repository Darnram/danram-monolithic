package com.danram.danram.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "party_member")
public class PartyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "int")
    private Long id;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @JoinColumn(name = "party_id")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Party party;

    @UpdateTimestamp
    @Column(name = "created_at", columnDefinition = "date")
    private LocalDate updatedAt;

    @Column(name = "deleted_at",columnDefinition = "date")
    private LocalDate deletedAt;
}
