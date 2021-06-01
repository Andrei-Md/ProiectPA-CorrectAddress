package com.example.springproject.algorithm.Scoring;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.FieldEnum;
import com.example.springproject.algorithm.model.ScoredAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.search.stringSearch.StringSearch;
import com.example.springproject.structures.AdmStructures;
import com.example.springproject.structures.entities.AdministrativeHierarchy;
import com.example.springproject.structures.entities.AdministrativeUnit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.springproject.algorithm.ScoreUtil.NO_UNIT_ADM_MAX;

public class BasicScoring implements AddressScoring {
    AdministrativeHierarchy administrativeHierarchy = AdmStructures.getAdministrativeHierarchy();

    @Override
    public List<ScoredAddress> addressScoring(BasicAddress basicAddress) {
        List<ScoredAddress> scoredAddressesList = new ArrayList<>();
        scoredAddressesList.add(scoreAddress(basicAddress));
        return scoredAddressesList;
    }

    private ScoredAddress stupidBasicConvert(BasicAddress basicAddress) {
        ScoredAddress scoredAddress = new ScoredAddress();
        scoredAddress.setCountry(basicAddress.getAdministrationFields().get(FieldEnum.Country.id()).get(0));
        scoredAddress.setState(basicAddress.getAdministrationFields().get(FieldEnum.State.id()).stream().max(Comparator.comparing((x) -> x.getScores().get(1))).orElse(null));
        scoredAddress.setCity(basicAddress.getAdministrationFields().get(FieldEnum.City.id()).stream().max(Comparator.comparing((x) -> x.getScores().get(2))).orElse(null));
        if (!basicAddress.getAdministrationFields().get(FieldEnum.PostalCode.id()).isEmpty()) {
            scoredAddress.setPostalCode(basicAddress.getAdministrationFields().get(FieldEnum.PostalCode.id()).get(0));
        }
        if (!basicAddress.getAdministrationFields().get(FieldEnum.StreetLine.id()).isEmpty()) {
            scoredAddress.setStreetLine(basicAddress.getAdministrationFields().get(FieldEnum.StreetLine.id()).get(0));
        }

//        scoredAddress.setCountry(basicAddress.getCountry().get(0));
//        scoredAddress.setState(basicAddress.getState().get(0));
//        scoredAddress.setCity(basicAddress.getCity().get(0));
//        scoredAddress.setPostalCode(basicAddress.getPostalCode().get(0));
//        scoredAddress.setStreetLine(basicAddress.getStreetLine().get(0));
        return scoredAddress;
    }

    private ScoredAddress scoreAddress(BasicAddress basicAddress) {
        StringSearch stringSearch = new StringSearch();
        List<ScoredAddress> scoredAddresses = new ArrayList<>();
        for (int i = NO_UNIT_ADM_MAX - 1; i >= 0; i--) {
            List<ScoredAdmUnit> scoredAdmUnitList = basicAddress.getAdministrationFields().get(i);
            for (int admIndex = 0; admIndex < scoredAdmUnitList.size(); admIndex++) {
                ScoredAdmUnit scoredAdmUnit = scoredAdmUnitList.get(admIndex);
                AdministrativeUnit administrativeUnit = scoredAdmUnit.getAdministrativeUnit();
                ScoredAddress scoredAddress = new ScoredAddress();
                while (administrativeUnit.getSuperDivision() != null) {
                    AdministrativeUnit superDivision = administrativeUnit.getSuperDivision();
                    List<Double> distances = stringSearch.getDamerauLevenshteinDistances(superDivision.getAsciiName(), basicAddress.getAdministrationFields().get(superDivision.getLevel()).stream().map((x) -> x.getAdministrativeUnit().getAsciiName()).collect(Collectors.toList()));
                    int max = 0;
                    double minDistance = 5;
                    for (int j = 0; j < distances.size(); j++) {
                        if (distances.get(j) < 5 && distances.get(j) < minDistance) {
                            minDistance = distances.get(j);
                            max = j;
                        }
                    }
                   // basicAddress.getFields().get(superDivision.getLevel()).get(max).addToScore(superDivision.getLevel(), (int) (50 - maxDistance * 10));
                    //basicAddress.getFields().get(currentAdministrativeUnit.getLevel()).get(admIndex).addToScore(currentAdministrativeUnit.getLevel(), (int) (50 - maxDistance * 10));
                    if(minDistance != 5){
                        scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), basicAddress.getAdministrationFields().get(superDivision.getLevel()).get(max), basicAddress.getAdministrationFields().get(superDivision.getLevel()).get(max).getScores().get(superDivision.getLevel()) + (int) ((20 - minDistance * 5)));
                    }else {
                        //scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), superDivision, 0);
                    }


                    administrativeUnit = administrativeUnit.getSuperDivision();
                }
                scoredAddress.setCity(scoredAdmUnit);
                scoredAddress.setCityScore(scoredAdmUnit.getScores().get(NO_UNIT_ADM_MAX - 1));
                scoredAddress.computeTotal();
                scoredAddresses.add(scoredAddress);
            }
        }
        return getBestScoredAddress(scoredAddresses);
    }

    private ScoredAddress getBestScoredAddress(List<ScoredAddress> scoredAddresses){
        ScoredAddress maxScoredAddress = new ScoredAddress();
        int maxScore = 0 ;
        for (ScoredAddress scoredAddress:
             scoredAddresses) {
            if(scoredAddress.getTotal() > maxScore){
                maxScore = scoredAddress.getTotal();
                maxScoredAddress = scoredAddress;
            }
        }
        return maxScoredAddress;
    }

}
