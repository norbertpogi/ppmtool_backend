package com.ppmtool.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<?> handleProjectIdException(ProjectIdException pex, WebRequest webReq) {
        ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(pex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<?> handleUsernameAlreadyExists(UsernameAlreadyExistsException uex, WebRequest webReq) {
        UsernameAlreadyExistsResponse userResponse = new UsernameAlreadyExistsResponse(uex.getMessage());
        return new ResponseEntity<>(userResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<?> handleProjectNotFoundException(ProjectNotFoundException pex, WebRequest webRequest) {
        ProjectNotFoundExceptionResponse prResponse = new ProjectNotFoundExceptionResponse(pex.getMessage());
        return new ResponseEntity<>(prResponse, HttpStatus.BAD_REQUEST);
    }
}
