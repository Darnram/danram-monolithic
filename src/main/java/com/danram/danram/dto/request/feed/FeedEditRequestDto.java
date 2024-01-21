package com.danram.danram.dto.request.feed;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedEditRequestDto {
    @NotNull
    private Long feedId;
    @Null
    private String content;
    @Null
    private MultipartFile image;
}
