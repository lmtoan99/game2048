package com.letoan.api;

import com.letoan.entity.UserEntity;
import com.letoan.exception.NotFoundException;
import com.letoan.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.letoan.model.*;
import com.letoan.exception.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;

@RestController
public class userAPI {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/user/register")
    public String createUser(@RequestBody RequestRegister rq){
        UserEntity check = userService.findByUsername(rq.getUsername());
        if (check != null){
            throw new CreatedException(rq.getUsername());
        }
        long create_time = Calendar.getInstance().getTime().getTime();
        String user_token = generateUserToken();

        String pwHash = hashPassword(rq.getPassword(),create_time);

        userService.save(new UserEntity(rq.getUsername(), pwHash, user_token, rq.getDisplay_name(), create_time));

        return user_token;
    }

    @PostMapping(value = "/user/login")
    public String loginUser(@RequestBody RequestLogin rq){
        UserEntity userEntity = userService.findByUsername(rq.getUsername());
        if (userEntity == null){
            throw new NotFoundException();
        }
        String raw_password = rq.getPassword();
        String hash_password = hashPassword(raw_password,userEntity.getCreateTime());

        if (hash_password.equals(userEntity.getPassword())){
            return userEntity.getUserToken();
        }
        else{
            throw new NotFoundException();
        }
    }

    private String hashPassword(String password, long salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(String.valueOf(salt).getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< 32 ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private String generateUserToken(){
        SecureRandom secureRandom = new SecureRandom(); //threadsafe
        Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
        byte[] randomBytes = new byte[128];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
