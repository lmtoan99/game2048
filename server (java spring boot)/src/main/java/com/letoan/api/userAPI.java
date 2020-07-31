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
    public UserEntity createUser(@RequestBody RequestRegister rq){
        UserEntity check = userService.findByUsername(rq.getUsername());
        if (check != null){
            throw new CreatedException(rq.getUsername());
        }
        long create_time = Calendar.getInstance().getTime().getTime();
        String user_token = generateUserToken();

        String pwHash = hashPassword(rq.getPassword(),create_time);

        UserEntity newUser = new UserEntity(rq.getUsername(), pwHash, user_token, rq.getDisplay_name(), create_time,0,0);
        userService.save(newUser);

        return newUser;
    }

    @PostMapping(value = "/user/login")
    public UserEntity loginUser(@RequestBody RequestLogin rq){
        UserEntity userEntity = userService.findByUsername(rq.getUsername());
        if (userEntity == null){
            throw new NotFoundException();
        }
        String raw_password = rq.getPassword();
        String hash_password = hashPassword(raw_password,userEntity.getCreateTime());

        if (hash_password.equals(userEntity.getPassword())){
            userEntity.setPassword(null);
            return userEntity;
        }
        else{
            throw new NotFoundException();
        }
    }


    @PostMapping(value = "/score/newhigh")
    public ResponseMessage newHighScore(@RequestBody RequestHighScore rq){
        UserEntity userEntity = userService.findByUserToken(rq.getUserToken());
        if (userEntity==null){
            throw new NotFoundException();
        }else {
            userEntity.setScore(rq.getScore());
            userEntity.setScoreTime(rq.getScoreTime());
            userService.save(userEntity);
            return new ResponseMessage("Success");
        }
    }

    @PostMapping(value = "/score/gethigh")
    public UserEntity getHighScore(@RequestBody RequestGet rq){
        UserEntity userEntity = userService.findByUserToken(rq.getUserToken());
        if (userEntity==null){
            throw new NotFoundException();
        }else{
            userEntity.setPassword(null);
            return userEntity;
        }
    }

    @GetMapping(value = "/score/toprank")
    public TopRank[] getTopRank(){
        return userService.getTopRank(3);
    }

    @PostMapping(value = "/score/myrank")
    public TopRank getMyRank(@RequestBody RequestGet rq){
        UserEntity userEntity = userService.findByUserToken(rq.getUserToken());
        int rank = userService.getRank(userEntity);
        return new TopRank(rank,userEntity.getDisplayName(),userEntity.getScore(),userEntity.getScoreTime());
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
