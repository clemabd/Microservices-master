package com.game.microfeedback.exceptions.feedback;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FeedbackNotFindException extends RuntimeException {
    public FeedbackNotFindException(String s) {
        super(s);
    }
}
