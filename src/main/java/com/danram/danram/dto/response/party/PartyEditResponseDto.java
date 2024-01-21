package com.danram.danram.dto.response.party;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyEditResponseDto {
    private Long partyId;
    private String img;
    private String title;
    private String description;
    private String password;
    private Long max;
    private String location;
    private Long managerId;
}
