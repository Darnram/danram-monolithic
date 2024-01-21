package com.danram.danram.dto.response.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthLoginResponseDto {
    private String nickname;
    private String email;
    private String profileImg;
    private Long loginType;
}
