package com.danram.danram.dto.response.member;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberInfoResponseDto {
    private String email;
    private String nickname;
    private String LoginType;
    private String img;
    private Boolean pro;
    private Boolean ban;
    private LocalDate createdAt;
}
