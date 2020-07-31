package com.letoan.service.impl;

import com.letoan.entity.UserEntity;
import com.letoan.model.TopRank;
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
    public TopRank[] getTopRank(int top) {
        List<UserEntity> query = userRepository.findAll(PageRequest.of(0,top, Sort.by(Sort.Direction.DESC,"score","scoreTime"))).getContent();
        TopRank[] rs = new TopRank[query.size()];
        for (int i = 0; i < query.size(); i++) {
            rs[i] = new TopRank();
            rs[i].setRank(i);
            rs[i].setDisplayName(query.get(i).getDisplayName());
            rs[i].setScore(query.get(i).getScore());
            rs[i].setScoreTime(query.get(i).getScoreTime());
        }
        return rs;
    }

    @Override
    public int getRank(UserEntity userEntity) {
        return userRepository.findRank(userEntity.getScore(),userEntity.getScoreTime());
    }
}
