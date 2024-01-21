package com.danram.danram.exception.comment;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class CommentLikeIdNotFoundException extends RuntimeException {
    private String message;

    public CommentLikeIdNotFoundException(final String email) {
        super(email);
        message = email;
    }
}
