package com.danram.danram.exception.member;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberLoginTypeNotExistException extends RuntimeException {
    private String message;

    public MemberLoginTypeNotExistException(Long loginType) {
        super(loginType.toString());
        message = loginType.toString();
    }
}
