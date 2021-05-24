package com.example.springproject.algorithm.search;

import com.example.springproject.algorithm.model.ScoreAddress;

import java.util.ArrayList;
import java.util.List;

public class FuzzySearch implements SearchInterface {

    private List<ScoreAddress> addressList = new ArrayList<>();

    @Override
    public List<ScoreAddress> search(List<ScoreAddress> scoreAddressList) {
        for (ScoreAddress scoreAddress : scoreAddressList) {
            scoreAddress.setCity("fuzzySearch");
            addressList.add(scoreAddress);
        }

        return new ArrayList<>(addressList);
    }
}
