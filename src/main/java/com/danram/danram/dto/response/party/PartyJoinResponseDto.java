package com.danram.danram.dto.response.party;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PartyJoinResponseDto {
    private Long id;
    private LocalDate createdAt;
}
