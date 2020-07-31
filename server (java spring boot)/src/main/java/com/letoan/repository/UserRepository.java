package com.letoan.repository;

import com.letoan.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    public UserEntity findOneByUsername(String username);
    public UserEntity findOneByUserToken(String user_token);

    @Query(value = "with abc as(select username, case when score > ?1 then true when score = ?1 then case when score_time < ?2 then true else false end else false end as rs from user) select count(*) from abc where rs = true", nativeQuery = true)
    public int findRank(int score, int scoreTime);
}
