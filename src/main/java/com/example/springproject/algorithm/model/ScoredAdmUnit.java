package com.example.springproject.algorithm.model;

import com.example.springproject.structures.entities.AdministrativeUnit;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.springproject.algorithm.ScoreUtil.MAX_NO_FIELD;

@Data
public class ScoredAdmUnit {

    AdministrativeUnit administrativeUnit;
    private List<Integer> scores;
    private int uniqueIdentifier;


    public ScoredAdmUnit(AdministrativeUnit administrativeUnit, int uniqueIdentifier) {
        this.administrativeUnit = administrativeUnit;

        scores = new ArrayList<>(Collections.nCopies(MAX_NO_FIELD, 0));
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public void setScores(int id, int bonus) {
        scores.set(id, bonus);
    }

    @Override
    public String toString() {
        return "ScoredAdmUnit{" +
                "scores=" + scores +
                ", uniqueIdentifier=" + uniqueIdentifier +
                ", " + administrativeUnit +
                '}';
    }
}
