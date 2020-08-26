package com.example.game2048.model;

public class TopRank {
    private int rank;
    private String displayName;
    private int score;

    public TopRank() {
    }

    public TopRank(int rank, String displayName, int score) {
        this.rank = rank;
        this.displayName = displayName;
        this.score = score;
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
}
