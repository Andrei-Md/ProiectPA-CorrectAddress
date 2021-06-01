package com.example.springproject.algorithm.model;

import com.example.springproject.structures.entities.AdministrativeUnit;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.example.springproject.algorithm.ScoreUtil.MAX_NO_FIELD;
import static com.example.springproject.algorithm.ScoreUtil.NO_UNIT_ADM_MAX;

@Data
public class BasicAddress {

    //    private List<ScoredAdmUnit> unknown = new ArrayList<>();
    private List<List<ScoredAdmUnit>> administrationFields;
    private List<SetMultimap<String, AdministrativeUnit>> administrationFieldsMap;

    private List<List<String>> nameFields;
    private List<String> unknown;


    public BasicAddress() {
        administrationFields = new ArrayList<>();
        for (int i = 0; i < MAX_NO_FIELD; i++) {
            administrationFields.add(new ArrayList<>());
        }
        administrationFieldsMap = initAdministrationFieldsMap();
        nameFields = initNameFields();
        unknown = new ArrayList<>();
    }

    private List<SetMultimap<String, AdministrativeUnit>> initAdministrationFieldsMap() {
        administrationFieldsMap = new ArrayList<>();
        for (int i = 0; i < MAX_NO_FIELD; i++) {
            administrationFieldsMap.add(HashMultimap.create());
        }
        return administrationFieldsMap;
    }

    private List<List<String>> initNameFields(){
        List<List<String>> retNameFields = new ArrayList<>();
        for (int i = 0; i < MAX_NO_FIELD; i++) {
            retNameFields.add(new ArrayList<>());
        }
        return retNameFields;
    }

    public void addAllAdmUnit(List<List<ScoredAdmUnit>> searchAdmUnitLists) {
        for (int i = 0; i < NO_UNIT_ADM_MAX; i++) {
            administrationFields.get(i).addAll(searchAdmUnitLists.get(i));
        }
//        for (List<ScoredAdmUnit> scoredAdmUnitList : searchAdmUnitLists) {
//
//        }
    }

    public void addNameField(String nameField, Integer id){
        nameFields.get(id).add(nameField);
    }

    public void addUnknown(String nameField){
        unknown.add(nameField);
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
