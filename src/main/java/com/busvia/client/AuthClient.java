package com.busvia.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

@HttpExchange

public interface AuthClient {

    @PostExchange("/auth/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap);
}
