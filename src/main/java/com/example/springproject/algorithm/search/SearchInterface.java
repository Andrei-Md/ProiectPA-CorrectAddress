package com.example.springproject.algorithm.search;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;

import java.util.List;

public interface SearchInterface {

    public List<ScoredAdmUnit> search(String entity);
}
