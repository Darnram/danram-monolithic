package com.danram.danram.controller;

import com.danram.danram.domain.Member;
import com.danram.danram.dto.request.login.OauthLoginRequestDto;
import com.danram.danram.dto.request.member.MemberEditRequestDto;
import com.danram.danram.dto.response.login.LoginResponseDto;
import com.danram.danram.dto.response.member.MemberInfoResponseDto;
import com.danram.danram.dto.response.member.MemberResponseDto;
import com.danram.danram.service.member.MemberService;
import com.danram.danram.service.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final S3UploadService s3UploadService;

    @PostMapping("/sign-up")
    public ResponseEntity<LoginResponseDto> signUp(@RequestBody OauthLoginRequestDto dto) {
        Optional<Member> result = memberService.checkDuplicatedEmail(dto.getEmail());

        if(result.isEmpty()) {
            return ResponseEntity.ok(memberService.signUp(dto));
        }
        else
        {
            return ResponseEntity.ok(memberService.signIn(result.get()));
        }
    }

    @GetMapping ("/verify")
    public ResponseEntity<String> tokenExist() {
        return ResponseEntity.ok(memberService.verifyMember());
    }

    //사용자 정보 조회
    @GetMapping
    public ResponseEntity<MemberResponseDto> getInfo() {
        return ResponseEntity.ok(memberService.getInfo());
    }

    //사용자 정보 수정
    @PostMapping(value = "/edit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MemberInfoResponseDto> editInfo(@ModelAttribute MemberEditRequestDto memberEditRequestDto) throws IOException {
        String upload = "";

        if(memberEditRequestDto.getImg() != null)
            upload = s3UploadService.upload(memberEditRequestDto.getImg(), "danram/profile", true);

        return ResponseEntity.ok(memberService.editInfo(memberEditRequestDto, upload));
    }

    //회원 탈퇴
    @DeleteMapping("/sign-out")
    public ResponseEntity signOut() {
        memberService.signOut();
        return ResponseEntity.ok().build();
    }
}
