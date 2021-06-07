package com.example.springproject.algorithm.search.stringSearch;

import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.search.admUnitSearch.AdmUnitSearchInterface;
import info.debatty.java.stringsimilarity.Damerau;

import java.util.ArrayList;
import java.util.List;

public class StringSearch {

    /**
     * method that gets the damerau levenshtein distances between a word
     * and every string from a list
     * @param wordToFind the word to find
     * @param stringsList the string list
     * @return a list of all the distances between the specified word and
     * the words from the list
     */
    public List<Double> getDamerauLevenshteinDistances(String wordToFind,List<String> stringsList){
        List<Double> foundDistances = new ArrayList<>();
        if(wordToFind.isEmpty()){
            return foundDistances;
        }
        Damerau damerau = new Damerau();
        for (String word: stringsList) {
            foundDistances.add(damerau.distance(wordToFind,word));
        }
        return foundDistances;
    }
}
