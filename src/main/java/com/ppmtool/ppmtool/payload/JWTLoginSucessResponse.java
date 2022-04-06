package com.ppmtool.ppmtool.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class JWTLoginSucessResponse {
    private Boolean success;
    private String token;
}
