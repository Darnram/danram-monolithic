package com.danram.danram.controller;

import com.danram.danram.dto.response.comment.CommentAllResponseDto;
import com.danram.danram.dto.response.feed.FeedAllInfoResponseDto;
import com.danram.danram.dto.response.party.PartyMemberResponseDto;
import com.danram.danram.dto.response.party.PartyResponseDto;
import com.danram.danram.dto.response.token.TokenResponseDto;
import com.danram.danram.service.comment.CommentService;
import com.danram.danram.service.feed.FeedService;
import com.danram.danram.service.member.MemberService;
import com.danram.danram.service.party.PartyService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/no-auth")
@RequiredArgsConstructor
@Slf4j
public class NoAuthController {
    private final MemberService memberService;
    private final PartyService partyService;
    private final FeedService feedService;
    private final CommentService commentService;

    @PostMapping("/token/reissue")
    public ResponseEntity<TokenResponseDto> reissueAccessToken() {
        return ResponseEntity.ok(memberService.reissueAccessToken());
    }

    @GetMapping("/party/search")
    public ResponseEntity<List<PartyResponseDto>> searchParty(@RequestParam Long category, @RequestParam Long sort, @RequestParam String query, @RequestParam Long page) {
        if(category == 0L)
        {
            if(query.isBlank()) {
                return ResponseEntity.ok(partyService.findParty(sort, Integer.parseInt(page.toString())));
            }
            else
            {
                return ResponseEntity.ok(partyService.findPartyBySearch(sort, query, Integer.parseInt(page.toString())));
            }
        }
        else
        {
            if(query.isBlank()) {
                return ResponseEntity.ok(partyService.findPartyByPartyType(category, sort, Integer.parseInt(page.toString())));
            }
            else
            {
                return ResponseEntity.ok(partyService.findPartyBySearchAndPartyType(sort, query, category, Integer.parseInt(page.toString())));
            }
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "모임에 참여중인 유저 조회 성공")
    })

    @GetMapping("/party/member/all")
    public ResponseEntity<List<PartyMemberResponseDto>> getPartyMember(@RequestParam Long partyId) {
        return ResponseEntity.ok(partyService.findPartyMember(partyId));
    }

    @GetMapping("/feed/all")
    public ResponseEntity<List<FeedAllInfoResponseDto>> getALl(@RequestParam Long partyId, @RequestParam Long page) {
        return ResponseEntity.ok(feedService.findAll(partyId, page));
    }

    @GetMapping("/comment/all")
    public ResponseEntity<List<CommentAllResponseDto>> getAllComment(@RequestParam Long feedId) {
        return ResponseEntity.ok(commentService.findAll(feedId));
    }

    @GetMapping("/auth/check")
    public ResponseEntity<List<String>> getAuthorities() {
        return ResponseEntity.ok(memberService.getAuthorities());
    }
}
