package com.danram.danram.service.oauth;

import com.danram.danram.dto.response.login.OauthLoginResponseDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverOauth implements SocialOauth {

    private final String naverBaseURL = "https://nid.naver.com/oauth2.0/authorize";
    private final String naverBaseTokenURL = "https://nid.naver.com/oauth2.0/token";
    private final String naverProfileURL = "https://openapi.naver.com/v1/nid/me";

    @Value("${sns.naver.id}")
    private String naverClientId;
    @Value("${sns.naver.url}")
    private String naverTokenRedirectURI;
    @Value("${sns.naver.secret}")
    private String naverClientSecret;

    private final Gson gson;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();

        params.put("response_type","code");
        params.put("client_id", naverClientId);
        params.put("redirect_uri", URLEncoder.encode(naverTokenRedirectURI,StandardCharsets.UTF_8));
        params.put("state",URLEncoder.encode(UUID.randomUUID().toString(),StandardCharsets.UTF_8));

        String redirectURI = "?"+params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return naverBaseURL+redirectURI;
    }

    @Override
    public String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<MultiValueMap<String,String>> httpEntity = getHttpEntity(code);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(naverBaseTokenURL,httpEntity,String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            JsonObject jsonObject = gson.fromJson(responseEntity.getBody(),JsonObject.class);
            return jsonObject.get("access_token").getAsString();
        }

        return "네이버 로그인 실패";
    }

    public String getUserProfile(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> header = new HttpEntity<>(headers);

        return restTemplate.exchange(naverProfileURL,HttpMethod.GET,header,String.class).getBody();
    }

    @Override
    public OauthLoginResponseDto getLoginResponseDto(String profileJson) {
        JsonObject jsonObject = gson.fromJson(profileJson, JsonObject.class);
        JsonObject profileElement = gson.fromJson(jsonObject.get("response"),JsonObject.class);

        return OauthLoginResponseDto.builder()
                .email(profileElement.get("email").getAsString())
                .nickname(profileElement.get("nickname").getAsString())
                .profileImg("profile_image")
                .loginType(0L)
                .build();
    }

    @Override
    public SocialLoginType type() {
        return SocialOauth.super.type();
    }

    private HttpEntity<MultiValueMap<String,String>> getHttpEntity(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept","application/json");

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",naverClientId);
        params.add("client_secret",naverClientSecret);
        params.add("code",code);
        params.add("state",URLEncoder.encode(UUID.randomUUID().toString(),StandardCharsets.UTF_8));

        return new HttpEntity<>(params, headers);
    }
}