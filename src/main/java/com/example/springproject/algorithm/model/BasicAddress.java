package com.example.springproject.algorithm.model;

import com.google.common.collect.SetMultimap;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.example.springproject.algorithm.ScoreUtil.MAX_NO_FIELD;
import static com.example.springproject.algorithm.ScoreUtil.NO_UNIT_ADM_MAX;

@Data
public class BasicAddress {
    private List<ScoredAdmUnit> country = new ArrayList<>();
    private List<ScoredAdmUnit> state = new ArrayList<>();
    private List<ScoredAdmUnit> city = new ArrayList<>();
    private List<ScoredAdmUnit> postalCode = new ArrayList<>(); //TODO maybe PostalCodeUnit with postal code state city .... only
    private List<ScoredAdmUnit> streetLine = new ArrayList<>();
    //    private List<ScoredAdmUnit> unknown = new ArrayList<>();
    private List<List<ScoredAdmUnit>> fields;

    public BasicAddress() {
        fields = new ArrayList<>();
        for (int i = 0; i < MAX_NO_FIELD; i++) {
            fields.add(new ArrayList<>());
        }
    }

    public void addAllAdmUnit(List<List<ScoredAdmUnit>> searchAdmUnitLists) {
        for (int i = 0; i < NO_UNIT_ADM_MAX; i++) {
            fields.get(i).addAll(searchAdmUnitLists.get(i));
        }
//        for (List<ScoredAdmUnit> scoredAdmUnitList : searchAdmUnitLists) {
//
//        }
    }

//    public void addCountry(ScoredAdmUnit scoredAdmUnit) {
//        this.country.add(scoredAdmUnit);
//    }
//
//    public void addCity(ScoredAdmUnit scoredAdmUnit) {
//        this.city.add(scoredAdmUnit);
//    }
//
//    public void addState(ScoredAdmUnit scoredAdmUnit) {
//        this.state.add(scoredAdmUnit);
//    }
//
//    public void addPostalCode(ScoredAdmUnit scoredAdmUnit) {
//        this.postalCode.add(scoredAdmUnit);
//    }
//
//    public void addStreetLine(ScoredAdmUnit scoredAdmUnit) {
//        this.streetLine.add(scoredAdmUnit);
//    }
//
//    //    public void addUnknown(ScoredAdmUnit scoredAdmUnit){        this.unknown.add(scoredAdmUnit);    }
//    public void addCountryAll(List<ScoredAdmUnit> scoredAdmUnit) {
//        this.country.addAll(scoredAdmUnit);
//    }
//
//    public void addCityAll(List<ScoredAdmUnit> scoredAdmUnit) {
//        this.city.addAll(scoredAdmUnit);
//    }
//
//    public void addStateAll(List<ScoredAdmUnit> scoredAdmUnit) {
//        this.state.addAll(scoredAdmUnit);
//    }
//
//    public void addPostalCodeAll(List<ScoredAdmUnit> scoredAdmUnit) {
//        this.postalCode.addAll(scoredAdmUnit);
//    }
//
//    public void addStreetLineAll(List<ScoredAdmUnit> scoredAdmUnit) {
//        this.streetLine.addAll(scoredAdmUnit);
//    }

//    public void addUnknownAll(List<ScoredAdmUnit> scoredAdmUnit){        this.unknown.addAll(scoredAdmUnit);    }
}
