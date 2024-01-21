package com.danram.danram.exception.party;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PartyMemberNotFoundException extends RuntimeException {
    private String message;

    public PartyMemberNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
