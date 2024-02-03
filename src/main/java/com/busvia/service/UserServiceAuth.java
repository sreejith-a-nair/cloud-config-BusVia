package com.busvia.service;


import com.busvia.model.UserRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserServiceAuth {
    ResponseEntity<String> changePassword(Map<String,String> requestMap);

    ResponseEntity<String> forgetPassword(Map<String, String> requestMap);

    void sendMail(String toEmail,
                  String subject,
                  String body);
}
