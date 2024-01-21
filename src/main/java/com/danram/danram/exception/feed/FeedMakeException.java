package com.danram.danram.exception.feed;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class FeedMakeException extends RuntimeException {
    private String message;

    public FeedMakeException(String message) {
        super(message);
        this.message = message;
    }
}
