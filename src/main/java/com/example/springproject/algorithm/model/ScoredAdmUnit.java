package com.example.springproject.algorithm.model;

import com.example.springproject.structures.entities.AdministrativeUnit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScoredAdmUnit {

    AdministrativeUnit administrativeUnit;
    private List<Score> scores;
    private int uniqueIdentifier;


    public ScoredAdmUnit(AdministrativeUnit administrativeUnit,int uniqueIdentifier) {
        this.administrativeUnit = administrativeUnit;
        scores = Score.getList();
        this.uniqueIdentifier = uniqueIdentifier;
    }


    public void setScores(int id, int bonus){
        scores.get(id).addScore(bonus);
    }
}
