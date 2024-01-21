package com.danram.danram.dto.response.feed;

import com.danram.danram.domain.Image;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FeedEditResponseDto {
    private Long feedId;
    private Long memberId;
    private String memberName;
    private Long likeNum;
    private LocalDateTime updatedAt;
    private List<Image> images;
    private String content;
}
