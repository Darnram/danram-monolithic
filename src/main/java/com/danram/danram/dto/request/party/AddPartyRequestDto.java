package com.danram.danram.dto.request.party;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddPartyRequestDto {
    private MultipartFile img;
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
