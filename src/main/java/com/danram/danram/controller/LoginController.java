package com.danram.danram.controller;

import com.danram.danram.dto.response.login.LoginResponseDto;
import com.danram.danram.dto.response.login.OauthLoginResponseDto;
import com.danram.danram.service.login.OAuthService;
import com.danram.danram.service.oauth.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final OAuthService oauthService;
    @Value("${gateway.url}")
    private String gatewayUrl;

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

        RestTemplate restTemplate = new RestTemplate();

        // 요청 매개변수 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OauthLoginResponseDto> request = new HttpEntity<>(oauthLoginResponseDto, headers);


        final LoginResponseDto response = restTemplate.exchange(gatewayUrl + "/member/sign-up", HttpMethod.POST, request, LoginResponseDto.class).getBody();

        return ResponseEntity.ok(response);
    }
}
