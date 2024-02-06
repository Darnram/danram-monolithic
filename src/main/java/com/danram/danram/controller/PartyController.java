package com.danram.danram.controller;

import com.danram.danram.domain.Party;
import com.danram.danram.dto.request.party.AddPartyRequestDto;
import com.danram.danram.dto.request.party.AddPartyWithoutImgRequestDto;
import com.danram.danram.dto.request.party.PartyEditRequestDto;
import com.danram.danram.dto.request.party.PartyJoinRequestDto;
import com.danram.danram.dto.response.party.*;
import com.danram.danram.service.party.PartyService;
import com.danram.danram.service.s3.S3UploadService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
@Slf4j
public class PartyController {
    /**
     * TODO
     * 비밀방
     * */
    private final S3UploadService s3UploadService;
    private final PartyService partyService;

    /*@ApiResponses({
            @ApiResponse(responseCode = "200",description = "모임 추가 성공")
    })
    @PostMapping(value= "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AddPartyResponseDto> addParty(@ModelAttribute AddPartyRequestDto dto) throws IOException {
        String imgUrl = null;

        log.error("dto: {}", dto);

        if (dto.getImg() != null) {
            imgUrl = s3UploadService.upload(dto.getImg(),"party_image", false);
        }
        else
            throw new FileNotFoundException("file is not exist.");

        return ResponseEntity.ok(partyService.addParty(dto,imgUrl));
    }*/

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "모임 추가 성공")
    })
    @PostMapping("/add/without-img")
    public ResponseEntity<AddPartyResponseDto> addParty(@RequestBody AddPartyWithoutImgRequestDto dto) throws IOException {
        return ResponseEntity.ok(partyService.addParty(dto));
    }

    @PostMapping(value = "/add/img", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PartyResponseDto> addImg(@RequestParam Long partyId, @RequestParam(name = "img") MultipartFile img) throws IOException {
        String imgUrl = null;

        log.info("part id: {}", partyId);

        if (img != null) {
            imgUrl = s3UploadService.upload(img,"party_image", false);
        }
        else
            throw new FileNotFoundException("file is not exist.");

        return ResponseEntity.ok(partyService.addImg(partyId, imgUrl));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "내 모임 조회")
    })
    @GetMapping("/my")
    public ResponseEntity<List<PartyResponseDto>> getMyParty(@RequestParam Integer pages) {
        return ResponseEntity.ok(partyService.findMyParty(pages));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "모임 조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<PartyResponseDto>> getParty(@RequestParam Long sortType,@RequestParam Integer pages) {
        return ResponseEntity.ok(partyService.findParty(sortType,pages));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "모임에 참여 성공")
    })
    @GetMapping("/join")
    public ResponseEntity<PartyJoinResponseDto> joinParty(@RequestParam Long partyId) {
        return ResponseEntity.ok(partyService.joinParty(new PartyJoinRequestDto(partyId)));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "방장 모임 삭제 성공")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteParty(@RequestParam Long partyId) {
        return ResponseEntity.ok(partyService.deleteParty(partyId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "모임 나가기 성공")
    })
    @PostMapping("/exit")
    public ResponseEntity<Boolean> exitParty(@RequestParam Long partyId) {
        return ResponseEntity.ok(partyService.exitParty(partyId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "모임 수정 성공")
    })
    @PostMapping("/edit")
    public ResponseEntity<PartyEditResponseDto> editParty(@ModelAttribute PartyEditRequestDto dto) throws IOException {
        String imgUrl = null;

        if (dto.getImg() != null) {
            imgUrl = s3UploadService.upload(dto.getImg(),"party_image", false);
        }

        return ResponseEntity.ok(partyService.editParty(dto,imgUrl));
    }
}
