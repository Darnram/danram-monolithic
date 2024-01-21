package com.danram.danram.controller;

import com.danram.danram.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final MemberService memberService;

    @GetMapping("/check")
    public ResponseEntity<List<String>> getAuthorities() {
        return ResponseEntity.ok(memberService.getAuthorities());
    }
}
