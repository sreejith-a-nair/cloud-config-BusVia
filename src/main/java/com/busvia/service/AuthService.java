package com.busvia.service;

import com.busvia.config.AuthConfig;
import com.busvia.entity.UserInfo;
import com.busvia.model.UserRequest;
import com.busvia.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private  UserInfoRepo userInfoRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  JwtService jwtService;

    public String saveUser(UserRequest userInfo){
  /* password encrypted in db*/
        UserInfo user = new UserInfo();
        user.setPassword(userInfo.getPassword());
        user.setEmail(userInfo.getEmail());
        user.setUuid(userInfo.getUuid());
        user.setRole(userInfo.getRole());
        user.setFirstName(userInfo.getFirstName());
        userInfoRepo.save(user);
        return "Registration success";
    }

    public List<UserInfo> findAll() {
       return userInfoRepo.findAll();
    }

//    UserDetails userDetail = userDetails.loadUserByUsername(userName);
//    public String generateToken(String username ){
//        return  jwtService.generateToken(username  );
//    }
//    public void validateToken(String token,UserDetails userDetails){
//          jwtService.validateToken(token,userDetails.getUsername());
//    }

//aagi user
//    $2a$10$jzjguCK7h4yIqAhV7eTyl.gEq5/In5gS5R4XtxwGQYZCjVG3QWasi


//aagi auth
//    $2a$10$FHGsA5ZRMDK0j/UUZoBzAeqgdS8RBL0iLGyeZaPDBEzCEH04jU4ky

//    auth
//    $2a$10$ujEfagtKrAVEuvrFcMiB9OFr88iGe9jpwr/heq5fGfuIShJguG9eG

//    $2a$10$ujEfagtKrAVEuvrFcMiB9OFr88iGe9jpwr/heq5fGfuIShJguG9eG

}
