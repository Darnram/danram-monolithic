package com.danram.danram.util;

import com.danram.danram.domain.Member;
import com.danram.danram.exception.ApiErrorResponse;
import com.danram.danram.exception.member.MemberIdNotFoundException;
import com.danram.danram.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtCustomFilter extends OncePerRequestFilter {
    @Value("${jwt.secret}")
    private String secretKey;
    private final MemberRepository memberRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            ApiErrorResponse errorResponse = new ApiErrorResponse("DEF-001", "JWT Token is null.");
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);

            return;
        }

        // bearer이 아니면 오류
        if (!authorizationHeader.startsWith("Bearer ")) {
            ApiErrorResponse errorResponse = new ApiErrorResponse("DEF-002", "JWT Token does not begin with Bearer String.");
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);

            return;
        }

        // Token 꺼내기
        String token = authorizationHeader.split(" ")[1];

        // Token 검증
        if (!JwtUtil.validateToken(token)) {
            ApiErrorResponse errorResponse = new ApiErrorResponse("DEF-003", "JWT Token is not valid");
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);

            return;
        }

        Long id = JwtUtil.getMemberId();

        final Optional<Member> memberOptional = memberRepository.findById(id);

        //member 검증
        final Member member = memberOptional.orElseThrow(
                () -> new MemberIdNotFoundException(id)
        );

        // Token 만료 체크
        if (JwtUtil.isExpired(token)) {
            ApiErrorResponse errorResponse = new ApiErrorResponse("DEF-004", "JWT Token is expired");
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);

            return;
        }

        String role;

        if(member.getRole().equals("ROLE_ADMIN")) {
            role = "ROLE_ADMIN";
        }
        else if(member.getRole().equals("ROLE_USER")) {
            role = "ROLE_USER";
        }
        else {
            ApiErrorResponse errorResponse = new ApiErrorResponse("DEF-005", "Member do not have permission.");
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);

            return;
        }

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member, null, List.of(new SimpleGrantedAuthority(role)));

        // UserDetail을 통해 인증된 사용자 정보를 SecurityContext에 저장
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}