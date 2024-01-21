package com.danram.danram.service.comment;

import com.danram.danram.dto.request.comment.CommentAddRequestDto;
import com.danram.danram.dto.request.comment.CommentEditRequestDto;
import com.danram.danram.dto.response.comment.CommentAddResponseDto;
import com.danram.danram.dto.response.comment.CommentAllResponseDto;
import com.danram.danram.dto.response.comment.CommentEditResponseDto;

import java.util.List;

public interface CommentService {
    public CommentAddResponseDto addComment(CommentAddRequestDto dto);
    public CommentEditResponseDto editComment(CommentEditRequestDto dto);
    public String deleteComment(Long commentId);
    public Long likeComment(Long commentId);
    public Long unlikeComment(Long commentId);
    public List<CommentAllResponseDto> findAll(Long feedId);
}
