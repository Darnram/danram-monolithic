package com.danram.danram.exception.feed;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
public class RoleNotExistException extends RuntimeException{
    public RoleNotExistException() {
        super();
    }
}
