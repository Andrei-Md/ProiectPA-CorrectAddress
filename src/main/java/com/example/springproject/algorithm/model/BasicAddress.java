package com.example.springproject.algorithm.model;

import com.example.springproject.model.Address;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BasicAddress {
    private List<ScoredAdmUnit> country = new ArrayList<>();
    private List<ScoredAdmUnit> state = new ArrayList<>();
    private List<ScoredAdmUnit> city = new ArrayList<>();
    private List<ScoredAdmUnit> postalCode = new ArrayList<>(); //TODO maybe PostalCodeUnit with postal code state city .... only
    private List<ScoredAdmUnit> streetLine = new ArrayList<>();
    private List<ScoredAdmUnit> unknown = new ArrayList<>();

    public void addCountry(ScoredAdmUnit scoredAdmUnit){
        this.country.add(scoredAdmUnit);
    }

    public void addCity(ScoredAdmUnit scoredAdmUnit){
        this.city.add(scoredAdmUnit);
    }

    public void addState(ScoredAdmUnit scoredAdmUnit){
        this.state.add(scoredAdmUnit);
    }

    public void addPostalCode(ScoredAdmUnit scoredAdmUnit){
        this.postalCode.add(scoredAdmUnit);
    }

    public void addStreetLine(ScoredAdmUnit scoredAdmUnit){
        this.streetLine.add(scoredAdmUnit);
    }

    public void addUnknown(ScoredAdmUnit scoredAdmUnit){
        this.unknown.add(scoredAdmUnit);
    }
    public void addCountryAll(List<ScoredAdmUnit> scoredAdmUnit){
        this.country.addAll(scoredAdmUnit);
    }

    public void addCityAll(List<ScoredAdmUnit> scoredAdmUnit){
        this.city.addAll(scoredAdmUnit);
    }

    public void addStateAll(List<ScoredAdmUnit> scoredAdmUnit){
        this.state.addAll(scoredAdmUnit);
    }

    public void addPostalCodeAll(List<ScoredAdmUnit> scoredAdmUnit){
        this.postalCode.addAll(scoredAdmUnit);
    }

    public void addStreetLineAll(List<ScoredAdmUnit> scoredAdmUnit){
        this.streetLine.addAll(scoredAdmUnit);
    }

    public void addUnknownAll(List<ScoredAdmUnit> scoredAdmUnit){
        this.unknown.addAll(scoredAdmUnit);
    }
}
