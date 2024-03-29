package com.danram.danram.service.feed;

import com.danram.danram.domain.*;
import com.danram.danram.dto.request.feed.FeedAddRequestDto;
import com.danram.danram.dto.request.feed.FeedEditRequestDto;
import com.danram.danram.dto.response.feed.FeedAddResponseDto;
import com.danram.danram.dto.response.feed.FeedAllInfoResponseDto;
import com.danram.danram.dto.response.feed.FeedEditResponseDto;
import com.danram.danram.dto.response.feed.FeedLikeResponseDto;
import com.danram.danram.enums.ReportEnum;
import com.danram.danram.exception.feed.FeedIdNotFoundException;
import com.danram.danram.exception.feed.FeedLikeIdNotFoundException;
import com.danram.danram.exception.feed.FeedMakeException;
import com.danram.danram.exception.feed.RoleNotExistException;
import com.danram.danram.exception.member.MemberIdNotFoundException;
import com.danram.danram.repository.*;
import com.danram.danram.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.danram.danram.config.MapperConfig.modelMapper;

@RequiredArgsConstructor
@Slf4j
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;
    private final ImageRepository imageRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final MemberRepository memberRepository;
    private final FeedReportRepository feedReportRepository;

    @Override
    @Transactional
    public FeedAddResponseDto addFeed(final FeedAddRequestDto dto, List<String> files) {
        FeedAddResponseDto map;

        //이미지 리스트 생성
        List<Image> images = new ArrayList<>();

        if(files.isEmpty()) {
            if(dto.getContent().isBlank()) {// 이미지 X, 글 X
                throw new FeedMakeException("no image and no content");
            }
            else
            { //이미지 없고 글 있는거
                for(String file: files)
                    images.add(imageRepository.save(
                            Image.builder()
                                    .memberId(JwtUtil.getMemberId())
                                    .description(dto.getMemberEmail() + "의 피드  사진")
                                    .memberEmail(dto.getMemberEmail())
                                    .imageUrl(file)
                                    .build()
                    ));

                Feed feed = feedRepository.save(
                        Feed.builder()
                                .memberId(JwtUtil.getMemberId())
                                .memberEmail(dto.getMemberEmail())
                                .content(dto.getContent())
                                .partyId(dto.getPartyId())
                                .images(images)
                                .description(dto.getMemberEmail() + "가 피드 생성")
                                .build()
                );

                map = modelMapper.map(feed, FeedAddResponseDto.class);

                return map;
            }
        }
        else
        { //이미지 있고 컨텐트는 노상관
            for(String image: files) { //이미지 저장 =>  객체로 만들기
                images.add(
                        imageRepository.save(
                                Image.builder()
                                        .imageUrl(image)
                                        .memberEmail(dto.getMemberEmail())
                                        .memberId(JwtUtil.getMemberId())
                                        .description(dto.getMemberEmail() + "의 피드 이미지")
                                        .build()
                        )
                );
            }

            Feed feed = feedRepository.save(
                    Feed.builder()
                            .memberId(JwtUtil.getMemberId())
                            .memberEmail(dto.getMemberEmail())
                            .deletedAt(null)
                            .content(dto.getContent().isBlank() ? null : dto.getContent())
                            .partyId(dto.getPartyId())
                            .images(images)
                            .description(dto.getMemberEmail() + "가 피드 생성")
                            .build()
            );

            map = modelMapper.map(feed, FeedAddResponseDto.class);

            return map;
        }
    }

    @Override
    @Transactional
    public List<FeedAllInfoResponseDto> findAll(final Long partyId, final Long page) {
        Slice<Feed> feedSlice = feedRepository.findByPartyIdAndDeletedAtIsNull(
                partyId,
                PageRequest.of(
                        Integer.parseInt(page.toString()),
                        10,
                        Sort.by(Sort.Direction.DESC, "updatedAt"))
        );

        List<FeedAllInfoResponseDto> responseDtoList = new ArrayList<>();

        for(Feed feed: feedSlice) {
            final String response = memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                    () -> new MemberIdNotFoundException(JwtUtil.getMemberId())
            ).getNickname();

            responseDtoList.add(
                    FeedAllInfoResponseDto.builder()
                            .feedId(feed.getFeedId())
                            .memberId(feed.getMemberId())
                            .memberName(response == null ? "이름 없음" : response)
                            .content(feed.getContent())
                            .images(feed.getImages())
                            .likeCount(
                                    Long.valueOf(feedLikeRepository.findByFeedIdAndDeletedFalse(feed.getFeedId()).size())
                            )
                            .hasNextSlice(feedSlice.hasNext())
                            .updatedAt(feed.getUpdatedAt())
                            .build()
            );
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public FeedEditResponseDto editFeed(final FeedEditRequestDto dto, final String img) {
        Feed feed = feedRepository.findById(dto.getFeedId()).orElseThrow(
                () -> new FeedIdNotFoundException(dto.getFeedId())
        );

        if(img != null)
            feed.setImages(List.of(
                    imageRepository.save(
                            Image.builder()
                                    .memberId(JwtUtil.getMemberId())
                                    .description(feed.getMemberEmail() + "의 " + feed.getFeedId() + "의 피드  사진")
                                    .imageUrl(img)
                                    .memberEmail(feed.getMemberEmail())
                                    .build())
                    )
            );

        if(dto.getContent() != null)
            feed.setContent(dto.getContent());

        FeedEditResponseDto map = modelMapper.map(feedRepository.save(feed), FeedEditResponseDto.class);

        final String response = memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(JwtUtil.getMemberId())
        ).getNickname();

        map.setMemberName(response == null ? "이름 없음" : response);

        return map;
    }

    @Override
    @Transactional
    public String deleteFeed(final Long feedId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new FeedIdNotFoundException(feedId)
        );

        final Member member = memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(JwtUtil.getMemberId())
        );

        if(feed.getMemberId() == JwtUtil.getMemberId()) {
            feed.setDeletedAt(LocalDateTime.now());
            feed.setDescription("본인이 지웠음");

            feedRepository.save(feed);

            return "Deleted by ";
        }
        else if(member.getAuthorities().contains("ROLE_ADMIN")) {
            feed.setDeletedAt(LocalDateTime.now());
            feed.setDescription("관리자에 의해 삭제");

            log.warn("관리자 멤버 아이디: {}", JwtUtil.getEmail());

            feedRepository.save(feed);

            return "Deleted by admin";
        }
        else {
            log.error("User has not authority: {}", JwtUtil.getEmail());

            throw new RoleNotExistException();
        }
    }

    @Override
    @Transactional
    public FeedLikeResponseDto likeFeed(final Long feedId) {
        Optional<FeedLike> likeOptional = feedLikeRepository.findByFeedIdAndMemberId(feedId, JwtUtil.getMemberId());

        if(likeOptional.isEmpty())
            feedLikeRepository.save(
                    FeedLike.builder()
                            .feedId(feedId)
                            .memberId(JwtUtil.getMemberId())
                            .memberEmail(JwtUtil.getEmail())
                            .deleted(false)
                            .build()
            );
        else
            likeOptional.get().setDeleted(false);

        final int size = feedLikeRepository.findByFeedIdAndDeletedFalse(feedId).size();

        return FeedLikeResponseDto.builder()
                .feedId(feedId)
                .likeCount((long) size)
                .build();
    }

    @Override
    @Transactional
    public FeedLikeResponseDto unlikeFeed(final Long feedId) {
        FeedLike likeOptional = feedLikeRepository.findByFeedIdAndMemberId(feedId, JwtUtil.getMemberId()).orElseThrow(
                () -> new FeedLikeIdNotFoundException(JwtUtil.getEmail())
        );

        likeOptional.setDeleted(true);


        final int size = feedLikeRepository.findByFeedIdAndDeletedFalse(feedId).size();

        return FeedLikeResponseDto.builder()
                .feedId(feedId)
                .likeCount((long) size)
                .build();
    }

    @Override
    @Transactional
    public FeedReport reportFeed(final Long feedId, final Long reportType) {
        final Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new FeedIdNotFoundException(feedId)
        );

        return feedReportRepository.save(
                FeedReport.builder()
                        .contentType(0L)
                        .description("feed: " + JwtUtil.getEmail() + "의 " + ReportEnum.findByValue(reportType) + "신고")
                        .reportType(reportType)
                        .memberId(JwtUtil.getMemberId())
                        .memberEmail(JwtUtil.getEmail())
                        .feedOwner(feed.getMemberEmail())
                        .build()
        );
    }
}
