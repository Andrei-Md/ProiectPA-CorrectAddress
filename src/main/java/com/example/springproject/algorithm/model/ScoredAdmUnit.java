package com.example.springproject.algorithm.model;

import com.example.springproject.structures.entities.AdministrativeUnit;
import lombok.Data;

@Data
public class ScoredAdmUnit {

    AdministrativeUnit administrativeUnit;
    private int countryScore;
    private int stateScore;
    private int cityScore;
    private int id;


    public ScoredAdmUnit(AdministrativeUnit administrativeUnit,int id) {
        this.administrativeUnit = administrativeUnit;
        countryScore = 0;
        stateScore = 0;
        cityScore = 0;
    }
}
