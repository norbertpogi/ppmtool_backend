package com.ppmtool.ppmtool.controller;

import com.ppmtool.ppmtool.domain.User;
import com.ppmtool.ppmtool.payload.JWTLoginSucessResponse;
import com.ppmtool.ppmtool.payload.LoginRequest;
import com.ppmtool.ppmtool.security.JwtTokenProvider;
import com.ppmtool.ppmtool.services.UserService;
import com.ppmtool.ppmtool.utilService.MapValidationErrorService;
import com.ppmtool.ppmtool.utilService.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ppmtool.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private MapValidationErrorService mapValidationErrorService;
    private UserService userService;
    private UserValidator userValidator;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(MapValidationErrorService mapValidationErrorService,
                          UserService userService,
                          UserValidator userValidator,
                          JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager
                          ) {
        this.mapValidationErrorService = mapValidationErrorService;
        this.userService = userService;
        this.userValidator = userValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
        if(errorMap != null)return errorMap;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
        JWTLoginSucessResponse jwtResponse = new JWTLoginSucessResponse(true,jwt);
        return new ResponseEntity<JWTLoginSucessResponse>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        // Validate passwords match
        userValidator.validate(user,result);

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null)return errorMap;

        User newUser = userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }
}
