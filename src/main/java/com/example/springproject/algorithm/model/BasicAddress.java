package com.example.springproject.algorithm.model;

import com.example.springproject.structures.entities.AdministrativeUnit;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import lombok.Data;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.springproject.algorithm.util.ScoreUtil.MAX_NO_FIELD;
import static com.example.springproject.algorithm.util.ScoreUtil.NO_UNIT_ADM_MAX;

@Data
public class BasicAddress {

    private List<Set<ScoredAdmUnit>> administrationFields;
    private List<SetMultimap<String, AdministrativeUnit>> administrationFieldsMap; //todo maybe needed

    private List<String> allAdmUnitNames;
    private List<List<String>> nameFields;

    public BasicAddress() {
        administrationFields = new ArrayList<>();
        for (int i = 0; i < MAX_NO_FIELD; i++) {
            administrationFields.add(new HashSet<>());//todo sorted set for score
        }
        administrationFieldsMap = initAdministrationFieldsMap();
        nameFields = initNameFields();
    }

    private List<SetMultimap<String, AdministrativeUnit>> initAdministrationFieldsMap() {
        administrationFieldsMap = new ArrayList<>();
        for (int i = 0; i < MAX_NO_FIELD; i++) {
            administrationFieldsMap.add(HashMultimap.create());
        }
        return administrationFieldsMap;
    }

    private List<List<String>> initNameFields() {
        List<List<String>> retNameFields = new ArrayList<>();
        for (int i = 0; i < MAX_NO_FIELD; i++) {
            retNameFields.add(new ArrayList<>());
        }
        return retNameFields;
    }

    /**
     * method used to add
     *
     * @param searchAdmUnitLists
     */
    public void addAllAdmUnit(List<List<ScoredAdmUnit>> searchAdmUnitLists) {
        for (int i = 0; i < NO_UNIT_ADM_MAX; i++) {
            if (searchAdmUnitLists.isEmpty()) return;
            administrationFields.get(i).addAll(searchAdmUnitLists.get(i));
        }
    }

    public void addNameField(String nameField, Integer id) {
        nameField = Normalizer.normalize(nameField, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");;
        nameFields.get(id).add(nameField);
    }

}
