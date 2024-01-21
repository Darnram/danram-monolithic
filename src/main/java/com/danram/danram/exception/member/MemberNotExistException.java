package com.danram.danram.exception.member;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class MemberNotExistException extends RuntimeException {
    public MemberNotExistException() {
        super();
    }
}
