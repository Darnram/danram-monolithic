package com.danram.danram.service.comment;

import com.danram.danram.domain.Comment;
import com.danram.danram.domain.CommentLike;
import com.danram.danram.dto.request.comment.CommentAddRequestDto;
import com.danram.danram.dto.request.comment.CommentEditRequestDto;
import com.danram.danram.dto.response.comment.CommentAddResponseDto;
import com.danram.danram.dto.response.comment.CommentAllResponseDto;
import com.danram.danram.dto.response.comment.CommentEditResponseDto;
import com.danram.danram.exception.comment.CommentIdNotFoundException;
import com.danram.danram.exception.comment.CommentLikeIdNotFoundException;
import com.danram.danram.exception.comment.NotAuthorityException;
import com.danram.danram.repository.CommentLikeRepository;
import com.danram.danram.repository.CommentRepository;
import com.danram.danram.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.danram.danram.config.MapperConfig.modelMapper;

@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    @Value("${gateway.url}")
    private String gatewayUrl;

    @Override
    @Transactional
    public CommentAddResponseDto addComment(final CommentAddRequestDto dto) {
        CommentAddResponseDto map = modelMapper.map(
                commentRepository.save(
                        Comment.builder()
                                .feedId(dto.getFeedId())
                                .parentId(dto.getParentId())
                                .content(dto.getContent())
                                .memberEmail(JwtUtil.getEmail())
                                .memberId(JwtUtil.getMemberId())
                                .deleted(false)
                                .build()
                ), CommentAddResponseDto.class
        );

        map.setLikeNum(0L);

        return map;
    }

    @Override
    @Transactional
    public CommentEditResponseDto editComment(final CommentEditRequestDto dto) {
        Comment comment = commentRepository.findById(dto.getCommentId()).orElseThrow(
                () -> new CommentIdNotFoundException(dto.getCommentId())
        );

        if(comment.getMemberId().equals(JwtUtil.getMemberId()))
            comment.setContent(dto.getContent());
        else
            throw new NotAuthorityException(JwtUtil.getEmail());

        return modelMapper.map(commentRepository.save(comment), CommentEditResponseDto.class);
    }

    @Override
    @Transactional
    public String deleteComment(final Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentIdNotFoundException(commentId)
        );

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON); // Content-Type 헤더 설정
        headers.set("Authorization", "Bearer " + JwtUtil.getAccessToken());
        headers.set("Member-Id", "DHI " + JwtUtil.getMemberId());

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        final List<String> auths = restTemplate.exchange(gatewayUrl + "/auth/check", HttpMethod.GET, requestEntity, List.class).getBody();


        if(comment.getMemberId() == JwtUtil.getMemberId()) {
            comment.setDeleted(true);

            return "Deleted by user";
        }
        else if(auths.contains("ROLE_ADMIN")) {
            comment.setDeleted(true);

            return "Deleted by admin";
        }
        else {
            throw new NotAuthorityException(JwtUtil.getEmail());
        }
    }

    @Override
    @Transactional
    public Long likeComment(final Long commentId) {
        Optional<CommentLike> likeOptional = commentLikeRepository.findByCommentIdAndMemberId(commentId, JwtUtil.getMemberId());

        if(likeOptional.isEmpty())
            commentLikeRepository.save(
                    CommentLike.builder()
                            .commentId(commentId)
                            .memberId(JwtUtil.getMemberId())
                            .memberEmail(JwtUtil.getEmail())
                            .deleted(false)
                            .build()
            );
        else
            likeOptional.get().setDeleted(false);

        final int size = commentLikeRepository.findByCommentIdAndDeletedFalse(commentId).size();

        return (long) size;
    }

    @Override
    public Long unlikeComment(final Long commentId) {
        CommentLike likeOptional = commentLikeRepository.findByCommentIdAndMemberId(commentId, JwtUtil.getMemberId()).orElseThrow(
                () -> new CommentLikeIdNotFoundException(JwtUtil.getEmail())
        );

        likeOptional.setDeleted(true);

        commentLikeRepository.save(likeOptional);


        final int size = commentLikeRepository.findByCommentIdAndDeletedFalse(commentId).size();

        return (long) size;
    }

    @Override
    @Transactional
    public List<CommentAllResponseDto> findAll(final Long feedId) {
        List<CommentAllResponseDto> responseDtoList = new ArrayList<>();

        final List<Comment> updatedAt = commentRepository.findByFeedIdAndDeletedFalse(feedId, Sort.by(Sort.Direction.ASC, "updatedAt"));
        RestTemplate restTemplate = new RestTemplate();

        for(Comment comment: updatedAt) {
            final int size = commentLikeRepository.findByCommentIdAndDeletedFalse(comment.getCommentId()).size();

            final String forObject = restTemplate.getForObject(gatewayUrl + "/member/nickname?id=" + comment.getMemberId(), String.class);

            responseDtoList.add(
                    CommentAllResponseDto.builder()
                            .commentId(comment.getCommentId())
                            .content(comment.getContent())
                            .likeCount((long) size)
                            .memberId(comment.getMemberId())
                            .memberName(forObject == null ? "이름 없음" : forObject)
                            .build()
            );
        }

        return responseDtoList;
    }
}
