package com.danram.danram.dto.request.comment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentAddRequestDto {
    private Long feedId;
    private Long parentId;
    private String content;
}
