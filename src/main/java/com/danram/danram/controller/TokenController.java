package com.danram.danram.controller;

import com.danram.danram.dto.response.token.TokenResponseDto;
import com.danram.danram.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
@Slf4j
public class TokenController {
    private final MemberService memberService;

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissueAccessToken() {
        return ResponseEntity.ok(memberService.reissueAccessToken());
    }
}
