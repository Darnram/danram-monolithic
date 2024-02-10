package com.danram.danram.service.member;

import com.danram.danram.domain.Member;
import com.danram.danram.dto.request.login.LoginRequestDto;
import com.danram.danram.dto.request.login.OauthLoginRequestDto;
import com.danram.danram.dto.request.member.MemberEditRequestDto;
import com.danram.danram.dto.response.login.LoginResponseDto;
import com.danram.danram.dto.response.login.OauthLoginResponseDto;
import com.danram.danram.dto.response.member.MemberAdminResponseDto;
import com.danram.danram.dto.response.member.MemberInfoResponseDto;
import com.danram.danram.dto.response.member.MemberResponseDto;
import com.danram.danram.dto.response.token.TokenResponseDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    public List<Member> findAll();
    public Optional<Member> checkDuplicatedEmail(String email);
    public LoginResponseDto signUp(OauthLoginResponseDto dto);
    public LoginResponseDto signUp(LoginRequestDto dto);
    public LoginResponseDto signIn(Member member);
    public List<String> getAuthorities();
    public String verifyMember();
    public MemberAdminResponseDto getMemberInfo(String memberId);
    public List<MemberAdminResponseDto> getMembers();
    public MemberResponseDto getInfo();
    public MemberInfoResponseDto editInfo(MemberEditRequestDto memberEditRequestDto, String upload);
    public void signOut();
    public TokenResponseDto reissueAccessToken();
    public String getInfo(Long id);
}
