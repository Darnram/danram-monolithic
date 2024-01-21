package com.danram.danram.dto.response.party;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartyMemberResponseDto {
    private Long memberId;
    private String nickname;
}
