package com.danram.danram.dto.response.token;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long memberId;
    private LocalDate accessTokenExpiredAt;
    private LocalDate refreshTokenExpiredAt;
}
