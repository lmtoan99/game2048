package com.letoan.model;

public class TopRank {
    Rank[] topRank;

    public TopRank() {
    }

    public TopRank(Rank[] topRank) {
        this.topRank = topRank;
    }

    public Rank[] getTopRank() {
        return topRank;
    }

    public void setTopRank(Rank[] topRank) {
        this.topRank = topRank;
    }
}
