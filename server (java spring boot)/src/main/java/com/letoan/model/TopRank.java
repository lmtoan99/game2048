package com.letoan.model;

public class TopRank {
    private int rank;
    private String displayName;
    private int score;
    private int scoreTime;

    public TopRank() {
    }

    public TopRank(int rank, String displayName, int score, int scoreTime) {
        this.rank = rank;
        this.displayName = displayName;
        this.score = score;
        this.scoreTime = scoreTime;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
