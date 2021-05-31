package com.example.springproject.algorithm.model;

import java.util.ArrayList;

public enum Score {
    Country(0, 0),
    State(1, 0),
    City(2, 0),
    PostalCode(3,0),
    StreetLine(4,0);

    private final int id;
    private int score;

    Score(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public static ArrayList<Score> getList(){
        return new ArrayList<Score>(){{
            add(Score.Country);
            add(Score.State);
            add(Score.City);
            add(Score.PostalCode);
            add(Score.StreetLine);
        }};
    }

    public int id() {
        return id;
    }

    public int score() {
        return score;
    }

    public void addScore(int addedScore) {
        this.score = this.score + addedScore;
    }

}
