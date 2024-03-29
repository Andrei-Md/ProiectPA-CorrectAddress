package com.example.springproject.algorithm.model;

import com.example.springproject.structures.entities.AdministrativeUnit;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.springproject.algorithm.util.ScoreUtil.MAX_NO_FIELD;

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

    /**
     * method that sets the score for the
     * scored administrative unit
     * @param id the score id
     * @param bonus the new score
     */
    public void setScores(int id, int bonus) {
        scores.set(id, bonus);
    }

    /**
     * method that adds to the score of
     * a scored administrative unit
     * @param id the score id
     * @param bonus the score that will be added
     */
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
        if (!(o instanceof ScoredAdmUnit)) return false;
        ScoredAdmUnit that = (ScoredAdmUnit) o;
        return Objects.equals(administrativeUnit, that.administrativeUnit) && Objects.equals(scores, that.scores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(administrativeUnit, scores);
    }
}
