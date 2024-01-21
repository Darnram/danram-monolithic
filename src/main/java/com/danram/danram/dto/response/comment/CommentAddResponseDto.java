package com.danram.danram.dto.response.comment;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentAddResponseDto {
    private Long commentId;
    private Long memberId;
    private String content;
    private Long likeNum;
    private LocalDateTime updatedAt;
    private Long feedId;
    private Long parentId;
}
