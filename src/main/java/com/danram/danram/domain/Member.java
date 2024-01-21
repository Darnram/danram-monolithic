package com.danram.danram.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Member {
    @Id
    @Column(name = "member_id", columnDefinition = "bigint")
    public Long memberId;

    @Column(name = "nickname", columnDefinition = "varchar", length = 30)
    private String nickname;

    @Column(name = "email", columnDefinition = "varchar", length = 50)
    private String email;

    @Column(name = "login_type", columnDefinition = "int")
    private Long loginType; //naver, kakako, apple

    @Column(name = "img", columnDefinition = "text")
    private String img;

    @Column(name = "pro", columnDefinition = "tinyint")
    private Boolean pro;

    @Column(name = "ban", columnDefinition = "tinyint")
    private Boolean ban;

    @Column(name = "access_token", columnDefinition = "text")
    private String accessToken;

    @Column(name = "access_token_expired_at", columnDefinition = "date")
    private LocalDate accessTokenExpiredAt;

    @Column(name = "refresh_token", columnDefinition = "text")
    private String refreshToken;

    @Column(name = "refresh_token_expired_at", columnDefinition = "date")
    private LocalDate refreshTokenExpiredAt;

    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private List<Authority> authorities;

    public String getRole() {
        for (Authority authority : authorities) {
            if(authority.getAuthorityName().equals("ROLE_ADMIN"))
                return "ROLE_ADMIN";
        }

        return "ROLE_USER";
    }
}
