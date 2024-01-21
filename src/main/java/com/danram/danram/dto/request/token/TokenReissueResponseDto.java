package com.danram.danram.dto.request.token;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TokenReissueResponseDto {
    private Long memberId;
    private String accessToken;
    private String refreshToken;
}
