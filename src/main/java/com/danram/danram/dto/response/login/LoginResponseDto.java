package com.danram.danram.dto.response.login;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private Long memberId;
    private String accessToken;
    private String refreshToken;
    private String nickname;
    private String email;
    private String img;
    private Boolean pro;
    private Boolean ban;
}
