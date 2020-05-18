package com.letoan.service;

import com.letoan.entity.UserEntity;

public interface IUserService {
    UserEntity save(UserEntity user);
    UserEntity findByUsername(String username);
}
