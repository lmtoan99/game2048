package com.letoan.repository;

import com.letoan.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    public UserEntity findOneByUsername(String username);
    public UserEntity findOneByUserToken(String user_token);

    @Query(value = "select count(*) from user where score > ?1", nativeQuery = true)
    public int findRank(int score);
}
