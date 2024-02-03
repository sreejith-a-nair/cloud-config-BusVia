package com.busvia.controller;

import com.busvia.model.LoginRequest;
import com.busvia.model.LoginResponse;
import com.busvia.model.UserRequest;
import com.busvia.service.AuthService;
import com.busvia.service.JwtService;
import com.busvia.service.UserServiceAuth;
import com.busvia.utils.AuthUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

     @Autowired
     private  UserDetailsService userDetailsService;
     @Autowired
     private  JwtService jwtService;
     @Autowired
     AuthenticationManager authenticationManager;

     @Autowired
     UserServiceAuth userServiceAuth;

     @Autowired
    AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws
            IndexOutOfBoundsException {
        System.out.println("login.. work: " + loginRequest);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        }
        catch (BadCredentialsException e) {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Login failed due to bad credentials");
            System.out.println("Login failed due to bad credentials");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());


        final String jwt = jwtService.generateToken(userDetails.getUsername(),userDetails.getAuthorities().toString());
        System.out.println("ROLE,,,,,"+userDetails.getAuthorities().toString());
        System.out.println("JWT * "+ jwt);

        return new LoginResponse(jwt);
}


    @PostMapping("/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String , String> requestMap){
        System.out.println("CHANGE PASSWORD METHOD WORKING *****");
        try{
            return userServiceAuth.changePassword(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return AuthUtil.getResponseEntity("Something went wrong !" , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/forgotPassword")
    ResponseEntity<String> forgotPassword(@RequestBody Map<String , String> requestMap){
        String email = requestMap.get("email");
        System.out.println("CHANGE PASSWORD METHOD WORKING *****"+email);
        try{
            return userServiceAuth.forgetPassword(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return AuthUtil.getResponseEntity("Something went wrong !" , HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @PostMapping("/forgotPassword")
//    ResponseEntity<String> forgotPassword(@RequestBody Map<String , String> requestMap){
//        String email = requestMap.get("email");
//        System.out.println("Received email: **********  0000" + email);
//
//        return AuthUtil.getResponseEntity("Something went wrong !" , HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }


    @PostMapping("/register")
    public String addNewUser(@RequestBody UserRequest userRequest) {
        System.out.println("AUTH REGISTER WORK "+userRequest);
        if(userRequest!=null) {
            authService.saveUser(userRequest);
            return "User added successfully";
        }
        return  "Request is empty";
    }

}
