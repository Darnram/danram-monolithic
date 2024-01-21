package com.danram.danram.dto.response.member;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberAdminResponseDto {
    private Long memberId;
    private String email;
    private String img;
    private Long LoginType;
    private String nickname;
    private Boolean ban;
    private Boolean pro;
    private String createdAt;
}
