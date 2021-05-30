package com.example.springproject.algorithm.Scoring;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAddress;

import java.util.ArrayList;
import java.util.List;

public class BasicScoring implements AddressScoring {
    @Override
    public List<ScoredAddress> addressScoring(BasicAddress basicAddress) {
        List<ScoredAddress> scoredAddressesList = new ArrayList<>();
        scoredAddressesList.add(stupidBasicConvert(basicAddress));
        return scoredAddressesList;
    }

    private ScoredAddress stupidBasicConvert(BasicAddress basicAddress) {
        ScoredAddress scoredAddress = new ScoredAddress();
        scoredAddress.setCountry(basicAddress.getCountry().get(0));
        scoredAddress.setState(basicAddress.getState().get(0));
        scoredAddress.setCity(basicAddress.getCity().get(0));
        scoredAddress.setPostalCode(basicAddress.getPostalCode().get(0));
        scoredAddress.setStreetLine(basicAddress.getStreetLine().get(0));
        return scoredAddress;
    }
}
