package com.danram.danram.dto.response.feed;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FeedLikeResponseDto {
    private Long feedId;
    private Long likeCount;
}
