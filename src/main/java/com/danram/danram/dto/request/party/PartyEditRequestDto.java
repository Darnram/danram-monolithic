package com.danram.danram.dto.request.party;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PartyEditRequestDto {
    private Long partyId;
    private MultipartFile img;
    private String title;
    private String description;
    private String password;
    private Long max;
    private String location;
    private Long managerId;
}
