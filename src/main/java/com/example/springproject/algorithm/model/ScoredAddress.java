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
}
