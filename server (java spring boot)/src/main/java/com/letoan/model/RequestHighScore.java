package com.letoan.model;

public class RequestHighScore {
    private String userToken;
    private int score;
    private int scoreTime;

    public RequestHighScore() {
    }

    public RequestHighScore(String userToken, int score, int scoreTime) {
        this.userToken = userToken;
        this.score = score;
        this.scoreTime = scoreTime;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
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
