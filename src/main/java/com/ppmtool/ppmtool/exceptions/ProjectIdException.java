package com.ppmtool.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException {
    //generate constructors to take the message String
    public ProjectIdException(String message) {
        super(message);
    }
}
