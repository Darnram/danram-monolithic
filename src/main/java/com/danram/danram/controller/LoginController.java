package com.danram.danram.controller;

import com.danram.danram.domain.Member;
import com.danram.danram.dto.request.login.LoginRequestDto;
import com.danram.danram.dto.response.login.LoginResponseDto;
import com.danram.danram.dto.response.login.OauthLoginResponseDto;
import com.danram.danram.service.login.OAuthService;
import com.danram.danram.service.member.MemberService;
import com.danram.danram.service.oauth.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final OAuthService oauthService;
    private final MemberService memberService;

    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);

        oauthService.request(socialLoginType);
    }

    @GetMapping(value = "/{socialLoginType}/token")
    public ResponseEntity<LoginResponseDto> codeCallBack(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) {
        OauthLoginResponseDto oauthLoginResponseDto = oauthService.getLoginResponseDto(socialLoginType,code);

        Optional<Member> result = memberService.checkDuplicatedEmail(oauthLoginResponseDto.getEmail());

        if(result.isEmpty()) {
            return ResponseEntity.ok(memberService.signUp(oauthLoginResponseDto));
        }
        else
        {
            return ResponseEntity.ok(memberService.signIn(result.get()));
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<LoginResponseDto> signUp(@RequestBody LoginRequestDto dto) {
        Optional<Member> result = memberService.checkDuplicatedEmail(dto.getEmail());

        if(result.isEmpty()) {
            return ResponseEntity.ok(memberService.signUp(dto));
        }
        else
        {
            return ResponseEntity.ok(memberService.signIn(result.get()));
        }
    }
}
