package com.letoan.service.impl;

import com.letoan.entity.UserEntity;
import com.letoan.repository.UserRepository;
import com.letoan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public UserEntity findByUsername(String username) {
        UserEntity userEntity = userRepository.findOneByUsername(username);
        return userEntity;
    }
}
