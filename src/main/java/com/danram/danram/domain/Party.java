package com.danram.danram.domain;

import com.danram.danram.dto.request.party.PartyEditRequestDto;
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
@Table(name = "party")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id", columnDefinition = "int")
    private Long partyId;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "member_email", columnDefinition = "varchar", length = 50)
    private String memberEmail;

    @Column(name = "img", columnDefinition = "text")
    private String img;

    @Column(name = "title", length = 100, columnDefinition = "varchar")
    private String title;

    @Column(name = "party_description", length = 200, columnDefinition = "varchar")
    private String description;

    @Column(name = "password", length = 6, columnDefinition = "varchar")
    private String password;

    @Column(name = "party_type", columnDefinition = "int")
    private Long partyType;

    @Column(name = "max", columnDefinition = "int")
    private Long max;

    @Column(name = "current_count",columnDefinition = "int")
    private Long currentCount;

    @Column(name = "started_at", columnDefinition = "date")
    private LocalDate startedAt;

    @Column(name = "end_at", columnDefinition = "date")
    private LocalDate endAt;

    @Column(name = "location", length = 50, columnDefinition = "varchar")
    private String location;

    @Column(name = "deleted", columnDefinition = "tinyint")
    private Boolean deleted;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "date")
    private LocalDate updatedAt;

    public void plusCurrentCount() {
        this.currentCount += 1L;
    }

    public void minusCurrentCount() {
        this .currentCount -= 1L;
    }

    public void updateParty(PartyEditRequestDto dto, String imgUrl) {
        if (imgUrl!=null) {
            this.img = imgUrl;
        }

        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.password = dto.getPassword();
        this.max = dto.getMax();
        this.location = dto.getLocation();
        this.memberId = dto.getManagerId();
    }
}
