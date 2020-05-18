package com.letoan.repository;

import com.letoan.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    public UserEntity findOneByUsername(String username);
    public UserEntity findOneByUserToken(String user_token);
}
