package com.letoan.service;

import com.letoan.entity.UserEntity;
import com.letoan.model.Rank;

public interface IUserService {
    UserEntity save(UserEntity user);
    UserEntity findByUsername(String username);
    UserEntity findByUserToken(String userToken);
    Rank[] getTopRank(int top);
    int getRank(UserEntity userEntity);
}
