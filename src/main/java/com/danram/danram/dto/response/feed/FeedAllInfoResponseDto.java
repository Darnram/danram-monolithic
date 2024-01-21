package com.danram.danram.dto.response.feed;

import com.danram.danram.domain.Image;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FeedAllInfoResponseDto {
    private Long feedId;
    private Long memberId;
    private String memberName;
    private LocalDateTime updatedAt;
    private List<Image> images;
    private String content;
    private Long likeCount;
    private Boolean hasNextSlice;
}
