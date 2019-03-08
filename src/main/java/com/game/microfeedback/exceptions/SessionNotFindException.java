package com.game.microfeedback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SessionNotFindException extends RuntimeException {
    public SessionNotFindException(String s) {
        super(s);
    }
}
