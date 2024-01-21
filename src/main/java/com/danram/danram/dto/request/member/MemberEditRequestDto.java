package com.danram.danram.dto.request.member;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberEditRequestDto {
    private String nickname;
    private MultipartFile img;
}
