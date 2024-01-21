package com.danram.danram.exception.comment;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class CommentIdNotFoundException extends RuntimeException {
    private String message;

    public CommentIdNotFoundException(final Long commentId) {
        super(commentId.toString());
        message = commentId.toString();
    }
}
