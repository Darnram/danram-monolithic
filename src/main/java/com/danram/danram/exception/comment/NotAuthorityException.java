package com.danram.danram.exception.comment;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
public class NotAuthorityException extends RuntimeException {
    private String message;

    public NotAuthorityException(final String email) {
        super(email);
        message = email;
    }
}
