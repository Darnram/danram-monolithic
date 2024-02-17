package com.danram.danram.dto.response.comment;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentAllResponseDto {
    private Long commentId;
    private Long memberId;
    private String memberName;
    private String content;
    private Long likeCount;
    private LocalDateTime createdAt;
}
