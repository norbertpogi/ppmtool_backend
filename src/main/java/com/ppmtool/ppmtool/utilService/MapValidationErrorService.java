package com.ppmtool.ppmtool.utilService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {

    public ResponseEntity<?> mapValidationService(BindingResult result) {
        if(result.hasErrors()) {
            Map<String, String> mapError = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                mapError.put(err.getField(), err.getDefaultMessage());
            });
            return new ResponseEntity<Map<String, String>>(mapError, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
