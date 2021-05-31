package com.example.springproject.algorithm.search.stringSearch;

import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.search.admUnitSearch.AdmUnitSearchInterface;
import info.debatty.java.stringsimilarity.Damerau;

import java.util.ArrayList;
import java.util.List;

public class StringSearch {

    public List<String> demerauLevenshteinSearch(String wordToFind,List<String> StringsList,double distance){
        List<String> foundWords = new ArrayList<>();
        Damerau damerau = new Damerau();
        for (String word: StringsList) {
            if(damerau.distance(wordToFind,word) <= distance){
                foundWords.add(word);
            }
        }
        return foundWords;
    }
}
