package com.danram.danram.controller;

import com.danram.danram.dto.request.feed.FeedAddRequestDto;
import com.danram.danram.dto.request.feed.FeedEditRequestDto;
import com.danram.danram.dto.response.feed.FeedAddResponseDto;
import com.danram.danram.dto.response.feed.FeedAllInfoResponseDto;
import com.danram.danram.dto.response.feed.FeedEditResponseDto;
import com.danram.danram.dto.response.feed.FeedLikeResponseDto;
import com.danram.danram.service.feed.FeedService;
import com.danram.danram.service.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
@Slf4j
public class FeedController {
    /**
     * TODO
     * 피드 신고
     * 알람 보기
     * 알람 추가
     * 알람 삭제
     * 알람 수정
     * */
    private final FeedService feedService;
    private final S3UploadService s3UploadService;

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FeedAddResponseDto> addFeed(@ModelAttribute FeedAddRequestDto dto) throws IOException {
        List<String> files = new ArrayList<>();

        if(dto.getImages().size() > 0) {
            for (MultipartFile file : dto.getImages()) {
                files.add(s3UploadService.upload(file, "/danram/feed", false));
            }
        }

        return ResponseEntity.ok(feedService.addFeed(dto, files));
    }

    @PostMapping(value = "/edit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FeedEditResponseDto> editFeed(@ModelAttribute FeedEditRequestDto dto) throws IOException {
        String img = null;

        if(dto.getImage() != null)
            img = s3UploadService.upload(dto.getImage(), "danram/feed", false);

        return ResponseEntity.ok(feedService.editFeed(dto, img));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFeed(@RequestParam Long feedId) {
        return ResponseEntity.ok(feedService.deleteFeed(feedId));
    }

    @GetMapping("/like")
    public ResponseEntity<FeedLikeResponseDto> likeFeed(@RequestParam Long feedId) {
        return ResponseEntity.ok(feedService.likeFeed(feedId));
    }

    @GetMapping("/unlike")
    public ResponseEntity<FeedLikeResponseDto> unlikeFeed(@RequestParam Long feedId) {
        return ResponseEntity.ok(feedService.unlikeFeed(feedId));
    }

    //free
    @GetMapping("/all")
    public ResponseEntity<List<FeedAllInfoResponseDto>> getALl(@RequestParam Long page) {
        return ResponseEntity.ok(feedService.findAll(page));
    }
}
