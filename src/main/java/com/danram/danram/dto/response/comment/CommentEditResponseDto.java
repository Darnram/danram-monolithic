package com.danram.danram.dto.response.comment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentEditResponseDto {
    private Long commentId;
    private String content;
}
