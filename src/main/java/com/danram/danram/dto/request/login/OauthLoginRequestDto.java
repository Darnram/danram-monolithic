package com.danram.danram.dto.request.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthLoginRequestDto {
    private String nickname;
    private String email;
    private String profileImg;
    private Long loginType;
}
