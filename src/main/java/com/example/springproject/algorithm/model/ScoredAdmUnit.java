package com.example.springproject.algorithm.model;

import com.example.springproject.structures.entities.AdministrativeUnit;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public void addToScore(int id, int bonus){
        scores.set(id,scores.get(id) + bonus);
    }

    @Override
    public String toString() {
        return "ScoredAdmUnit{" +
                "scores=" + scores +
                ", uniqueIdentifier=" + uniqueIdentifier +
                ", " + administrativeUnit +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoredAdmUnit that = (ScoredAdmUnit) o;
        return Objects.equals(administrativeUnit, that.administrativeUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(administrativeUnit);
    }
}
