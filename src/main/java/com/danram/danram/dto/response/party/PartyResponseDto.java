package com.danram.danram.dto.response.party;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PartyResponseDto {
    private Long partyId;
    private String img;
    private String title;
    private String password;
    private LocalDate startedAt;
    private LocalDate endAt;
    private String location;
    private Long max;
    private Long currentCount;
    private String partyType;
    private Boolean hasNextSlice;
}
