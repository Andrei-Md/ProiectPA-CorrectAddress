package com.example.springproject.algorithm.search;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.structures.entities.AdministrativeUnit;

import java.util.ArrayList;
import java.util.List;

public class FuzzySearch implements SearchInterface {

    private List<BasicAddress> addressList = new ArrayList<>();

    @Override
    public List<ScoredAdmUnit> search(String entity) {
        //todo search
        String[] row = {"686579","Comuna Alb","Comuna Alb","","46.45246","22.95027","RO"};
        ScoredAdmUnit scoredAdmUnit = new ScoredAdmUnit(new AdministrativeUnit(row,","),0);
        List<ScoredAdmUnit> scoredAdmUnitList = new ArrayList<>();
        scoredAdmUnitList.add(scoredAdmUnit);
        return scoredAdmUnitList;
    }
}
