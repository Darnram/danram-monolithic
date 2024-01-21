package com.danram.danram.exception.feed;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class FeedLikeIdNotFoundException extends RuntimeException {
    private String message;
    public FeedLikeIdNotFoundException(final String email) {
        super(email);
        message = email;
    }
}
