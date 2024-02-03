package com.busvia.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    private AuthUtil(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return  new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}",httpStatus);
    }
}
