package com.danram.danram.controller;

import com.danram.danram.dto.request.comment.CommentAddRequestDto;
import com.danram.danram.dto.request.comment.CommentEditRequestDto;
import com.danram.danram.dto.response.comment.CommentAddResponseDto;
import com.danram.danram.dto.response.comment.CommentAllResponseDto;
import com.danram.danram.dto.response.comment.CommentEditResponseDto;
import com.danram.danram.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<CommentAddResponseDto> addComment(@RequestBody CommentAddRequestDto dto) {
        return ResponseEntity.ok(commentService.addComment(dto));
    }

    @PostMapping("/edit")
    public ResponseEntity<CommentEditResponseDto> editComment(@RequestBody CommentEditRequestDto dto) {
        return ResponseEntity.ok(commentService.editComment(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId) {
        return ResponseEntity.ok(commentService.deleteComment(commentId));
    }

    @GetMapping("/like")
    public ResponseEntity<Long> likeComment(@RequestParam Long commentId) {
        return ResponseEntity.ok(commentService.likeComment(commentId));
    }

    @GetMapping("/unlike")
    public ResponseEntity<Long> unlikeComment(@RequestParam Long commentId) {
        return ResponseEntity.ok(commentService.unlikeComment(commentId));
    }

    //free
    @GetMapping("/all")
    public ResponseEntity<List<CommentAllResponseDto>> getAllComment(@RequestParam Long feedId) {
        return ResponseEntity.ok(commentService.findAll(feedId));
    }
}
