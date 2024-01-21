package com.danram.danram.exception.party;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidHostException extends RuntimeException {
    private String message;

    public InvalidHostException(String message) {
        super(message);
        this.message = message;
    }
}
