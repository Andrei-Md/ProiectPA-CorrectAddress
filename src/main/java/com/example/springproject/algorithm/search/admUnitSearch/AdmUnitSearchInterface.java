package com.example.springproject.algorithm.search.admUnitSearch;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.google.common.collect.SetMultimap;

import java.util.List;

public interface AdmUnitSearchInterface {

    public List<List<ScoredAdmUnit>> search(String entity, int id, int bonus);
}
