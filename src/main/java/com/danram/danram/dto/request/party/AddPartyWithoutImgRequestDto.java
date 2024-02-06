package com.danram.danram.dto.request.party;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPartyWithoutImgRequestDto {
    private String title;
    private String description;
    private String password;
    private Long partyType;
    private Long max;
    private String location;
    private String memberEmail;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt;
}
