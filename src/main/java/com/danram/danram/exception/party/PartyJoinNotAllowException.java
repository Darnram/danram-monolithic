package com.danram.danram.exception.party;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PartyJoinNotAllowException extends RuntimeException {
    public PartyJoinNotAllowException() {
        super("party join not allowed - 3 days still not passed");
    }
}
