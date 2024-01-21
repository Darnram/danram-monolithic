package com.danram.danram.dto.response.member;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String img;
    private Boolean pro;
    private Boolean ban;
}
