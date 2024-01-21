package com.danram.danram.exception.s3;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class FileNameNotValidException extends RuntimeException {
    public FileNameNotValidException() {
        super();
    }
}
