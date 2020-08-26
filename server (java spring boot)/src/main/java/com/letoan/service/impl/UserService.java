package com.letoan.service.impl;

import com.letoan.entity.UserEntity;
import com.letoan.model.Rank;
import com.letoan.repository.UserRepository;
import com.letoan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UserEntity findByUserToken(String userToken) {
        UserEntity userEntity = userRepository.findOneByUserToken(userToken);
        return userEntity;
    }

    @Override
    public Rank[] getTopRank(int top) {
        List<UserEntity> query = userRepository.findAll(PageRequest.of(0,top, Sort.by(Sort.Direction.DESC,"score"))).getContent();
        Rank[] rs = new Rank[query.size()];
        for (int i = 0; i < query.size(); i++) {
            rs[i] = new Rank();
            rs[i].setRank(i + 1);
            rs[i].setDisplayName(query.get(i).getDisplayName());
            rs[i].setScore(query.get(i).getScore());
        }
        return rs;
    }

    @Override
    public int getRank(UserEntity userEntity) {
        return userRepository.findRank(userEntity.getScore()) + 1;
    }
}
