package com.letoan.model;

public class Rank {
    private int rank;
    private String displayName;
    private int score;

    public Rank() {
    }

    public Rank(int rank, String displayName, int score) {
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
