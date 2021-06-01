package com.example.springproject.algorithm.model;

import lombok.Data;

@Data
public class ScoredAddress {

    private ScoredAdmUnit country;
    private ScoredAdmUnit state;
    private ScoredAdmUnit city;
    private ScoredAdmUnit postalCode;
    private ScoredAdmUnit streetLine;

    private int countryScore;
    private int stateScore;
    private int cityScore;
    private int postalCodeScore;
    private int streetLineScore;
    private int total;

    public void setScoreBasedOnLevel(int level, ScoredAdmUnit scoredAdmUnit,int score){
        if(level == 0){
            this.setCountry(scoredAdmUnit);
            this.setCountryScore(score);
        }
        if(level == 1){
            this.setState(scoredAdmUnit);
            this.setStateScore(score);
        }
        if(level == 2){
            this.setCity(scoredAdmUnit);
            this.setCityScore(score);
        }
    }

    public void computeTotal(){
        this.total = countryScore + cityScore + stateScore;
    }
}
