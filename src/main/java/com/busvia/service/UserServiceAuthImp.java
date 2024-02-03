package com.busvia.service;

import com.busvia.config.JwtRequestFilter;
import com.busvia.entity.UserInfo;
import com.busvia.model.UserRequest;
import com.busvia.repository.UserInfoRepo;
import com.busvia.utils.AuthUtil;
import com.busvia.utils.EmailUtils;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceAuthImp implements UserServiceAuth {

    @Autowired
    UserInfoRepo userInfoRepo;
    @Autowired
    JwtRequestFilter jwtFilter;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    JavaMailSender mailSender;

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {

        try {
            String userEmail = jwtFilter.getCurrentUSer();

            Optional<UserInfo> user = userInfoRepo.findByEmail(userEmail);
            UserInfo userObj = user.orElseThrow();

            String oldPassword = requestMap.get("oldPassword");
            String storedHashedPassword = userObj.getPassword();


            if (passwordEncoder.matches(oldPassword, storedHashedPassword)) {
                System.out.println("Passords are matched >>>>>>>>"+oldPassword);

                String newPassword = requestMap.get("newPassword");
                System.out.println("Passords are matched >>>>>>>>OLD "+oldPassword +"NEW "+newPassword);
                userObj.setPassword(passwordEncoder.encode(newPassword));
                userInfoRepo.save(userObj);
                return AuthUtil.getResponseEntity("Password changed successfully", HttpStatus.OK);
            } else {
                return AuthUtil.getResponseEntity("Incorrect old password", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return AuthUtil.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> forgetPassword(Map<String, String> requestMap) {
        System.out.println("work 1 ");

        try {
            Optional<UserInfo> userInfo = userInfoRepo.findByEmail(requestMap.get("email"));
            UserInfo userObj = userInfo.get();
            System.out.println("work 2 " +userObj.getEmail());
            if (!Objects.isNull(userObj) && !Strings.isNullOrEmpty(userObj.getEmail())) {
                System.out.println("work 3 ");
                String emailId=userObj.getEmail();
                String password=userObj.getPassword();
                String subject = "Credentials by BusVia system";
                System.out.println("EMAIL >>>>>>4 "+emailId + " Password "+password);
                emailUtils.sendMail(emailId, subject, password);

          return AuthUtil.getResponseEntity("Check you email for credentials",HttpStatus.OK);}
        } catch (Exception ex) {
            System.out.println("work 4 ");
            ex.printStackTrace();
        }
        System.out.println("work 5 ");
        return AuthUtil.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public void sendMail(String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("anairsreejith1998@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

        System.out.println("Mail sent successfully.......");
    }

//ganganbipin@gmail.com
}

