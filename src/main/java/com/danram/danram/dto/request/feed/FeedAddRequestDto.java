package com.danram.danram.dto.request.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedAddRequestDto {
    /**
     * 확장성을 위해 Image list로 하고 실제로는 하나만
     * */
    private String memberEmail;
    private Long partyId;
    private List<MultipartFile> images;
    private String content;
}
