package com.danram.danram.config;

import com.amazonaws.services.s3.AmazonS3;
import com.danram.danram.repository.*;
import com.danram.danram.service.feed.FeedService;
import com.danram.danram.service.feed.FeedServiceImpl;
import com.danram.danram.service.member.MemberService;
import com.danram.danram.service.member.MemberServiceImpl;
import com.danram.danram.service.party.PartyService;
import com.danram.danram.service.party.PartyServiceImpl;
import com.danram.danram.service.s3.S3UploadService;
import com.danram.danram.service.s3.S3UploadServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {
    @Bean
    public MemberService memberService(MemberRepository memberRepository, DeletedMemberRepository deletedMemberRepository) {
        return new MemberServiceImpl(memberRepository, deletedMemberRepository);
    }

    @Bean
    public S3UploadService s3UploadService(AmazonS3 amazonS3) {
        return new S3UploadServiceImpl(amazonS3);
    }

    @Bean
    public FeedService feedService(final FeedRepository feedRepository, final ImageRepository imageRepository, final FeedLikeRepository feedLikeRepository, final MemberRepository memberRepository) {
        return new FeedServiceImpl(feedRepository, imageRepository, feedLikeRepository, memberRepository);
    }

    @Bean
    public PartyService partyService(final PartyRepository partyRepository, final PartyMemberRepository partyMemberRepository,
                                    final MemberRepository memberRepository) {
        return new PartyServiceImpl(partyRepository, partyMemberRepository, memberRepository);
    }
}