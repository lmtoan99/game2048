package com.letoan.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "userToken")
    private String userToken;
    @Column(name = "displayName")
    private String displayName;
    @Column(name = "createTime")
    private long createTime;
    @Column(name = "score")
    private int score;
    @Column(name = "scoreTime")
    private int scoreTime;

    public UserEntity() {
    }

    /*public UserEntity(String username, String password, String userToken, String displayName, long createTime) {
        this.username = username;
        this.password = password;
        this.userToken = userToken;
        this.displayName = displayName;
        this.createTime = createTime;
        System.out.println("UserEntity: " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createTime));
        this.score = 0;
        this.scoreTime = 0;
    }*/

    public UserEntity(String username, String password, String userToken, String displayName, long createTime, int score, int scoreTime) {
        this.username = username;
        this.password = password;
        this.userToken = userToken;
        this.displayName = displayName;
        this.createTime = createTime;
        this.score = score;
        this.scoreTime = scoreTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String display_name) {
        this.displayName = display_name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(int scoreTime) {
        this.scoreTime = scoreTime;
    }
}
