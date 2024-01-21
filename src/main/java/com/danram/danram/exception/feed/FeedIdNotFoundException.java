package com.danram.danram.exception.feed;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class FeedIdNotFoundException extends RuntimeException {
    private String message;

    public FeedIdNotFoundException(final Long feedId) {
        super(feedId.toString());
        message = feedId.toString();
    }
}
