package com.danram.danram.dto.response.party;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddPartyResponseDto {
    private Long partyId;
    private String memberEmail;
    private String img;
    private String title;
    private String description;
    private Long partyType;
    private Long max;
    private LocalDate startedAt;
    private LocalDate endAt;
    private String location;
    private LocalDate updatedAt;
    private Long currentCount;
}
