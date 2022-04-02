package com.ppmtool.ppmtool.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameAlreadyExistsResponse {

    private String username;

    public UsernameAlreadyExistsResponse(String username) {
        this.username = username;
    }
}
