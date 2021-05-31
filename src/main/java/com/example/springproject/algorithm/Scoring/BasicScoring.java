package com.example.springproject.algorithm.Scoring;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.Score;
import com.example.springproject.algorithm.model.ScoredAddress;
import com.example.springproject.structures.AdmStructures;
import com.example.springproject.structures.entities.AdministrativeHierarchy;

import java.util.ArrayList;
import java.util.List;

public class BasicScoring implements AddressScoring {
    AdministrativeHierarchy administrativeHierarchy = AdmStructures.getAdministrativeHierarchy();

    @Override
    public List<ScoredAddress> addressScoring(BasicAddress basicAddress) {
        List<ScoredAddress> scoredAddressesList = new ArrayList<>();
        scoredAddressesList.add(stupidBasicConvert(basicAddress));
        return scoredAddressesList;
    }

    private ScoredAddress stupidBasicConvert(BasicAddress basicAddress) {
        ScoredAddress scoredAddress = new ScoredAddress();
        scoredAddress.setCountry(basicAddress.getFields().get(Score.Country.id()).get(0));
        scoredAddress.setState(basicAddress.getFields().get(Score.State.id()).get(0));
        scoredAddress.setCity(basicAddress.getFields().get(Score.City.id()).get(0));
        if(!basicAddress.getFields().get(Score.PostalCode.id()).isEmpty()){
            scoredAddress.setPostalCode(basicAddress.getFields().get(Score.PostalCode.id()).get(0));
        }
        if(!basicAddress.getFields().get(Score.StreetLine.id()).isEmpty()){
            scoredAddress.setStreetLine(basicAddress.getFields().get(Score.StreetLine.id()).get(0));
        }
//        scoredAddress.setCountry(basicAddress.getCountry().get(0));
//        scoredAddress.setState(basicAddress.getState().get(0));
//        scoredAddress.setCity(basicAddress.getCity().get(0));
//        scoredAddress.setPostalCode(basicAddress.getPostalCode().get(0));
//        scoredAddress.setStreetLine(basicAddress.getStreetLine().get(0));
        return scoredAddress;
    }
}
