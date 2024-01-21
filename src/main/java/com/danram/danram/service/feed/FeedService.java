package com.danram.danram.service.feed;


import com.danram.danram.dto.request.feed.FeedAddRequestDto;
import com.danram.danram.dto.request.feed.FeedEditRequestDto;
import com.danram.danram.dto.response.feed.FeedAddResponseDto;
import com.danram.danram.dto.response.feed.FeedAllInfoResponseDto;
import com.danram.danram.dto.response.feed.FeedEditResponseDto;
import com.danram.danram.dto.response.feed.FeedLikeResponseDto;

import java.util.List;

public interface FeedService {
    public FeedAddResponseDto addFeed(FeedAddRequestDto dto, List<String> files);
    public List<FeedAllInfoResponseDto> findAll(Long page);
    public FeedEditResponseDto editFeed(FeedEditRequestDto dto, String img);
    public String deleteFeed(Long feedId);
    public FeedLikeResponseDto likeFeed(Long feedId);
    public FeedLikeResponseDto unlikeFeed(final Long feedId);
}
