package com.example.springproject.algorithm.model;

import lombok.Data;

/**
 * class that encapsulates a scored address
 */
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

    /**
     * method used to set the score based on the administrative unit level
     * @param level the administrative unit's level
     * @param scoredAdmUnit the scored administrative unit
     * @param score the score that will be set
     */
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


    /**
     * method that computes the total score
     * for the current scored address
     */
    public void computeTotal(){
        this.total = countryScore + cityScore + stateScore;
    }

    @Override
    public String toString() {
        return "ScoredAddress{" +
                "total=" + total +
                ", country=" + country +
                ", state=" + state +
                ", city=" + city +
                ", postalCode=" + postalCode +
                ", streetLine=" + streetLine +
                ", countryScore=" + countryScore +
                ", stateScore=" + stateScore +
                ", cityScore=" + cityScore +
                ", postalCodeScore=" + postalCodeScore +
                ", streetLineScore=" + streetLineScore +
                '}';
    }
}
