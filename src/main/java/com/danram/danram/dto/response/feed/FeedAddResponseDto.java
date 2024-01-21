package com.danram.danram.dto.response.feed;

import com.danram.feed.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedAddResponseDto {
    /**
     * 확장성을 위해 image list로 실제로는 이미지 하나만
     * */
    private Long feedId;
    private List<Image> images;
    private String content;
}
