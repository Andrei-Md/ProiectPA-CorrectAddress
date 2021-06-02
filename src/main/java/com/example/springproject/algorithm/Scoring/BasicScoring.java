package com.example.springproject.algorithm.Scoring;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.FieldEnum;
import com.example.springproject.algorithm.model.ScoredAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.search.stringSearch.StringSearch;
import com.example.springproject.structures.AdmStructures;
import com.example.springproject.structures.entities.AdministrativeHierarchy;
import com.example.springproject.structures.entities.AdministrativeUnit;

import java.util.*;
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
//
//    private ScoredAddress stupidBasicConvert(BasicAddress basicAddress) {
//        ScoredAddress scoredAddress = new ScoredAddress();
//        scoredAddress.setCountry(basicAddress.getAdministrationFields().get(FieldEnum.Country.id()).get(0));
//        scoredAddress.setState(basicAddress.getAdministrationFields().get(FieldEnum.State.id()).stream().max(Comparator.comparing((x) -> x.getScores().get(1))).orElse(null));
//        scoredAddress.setCity(basicAddress.getAdministrationFields().get(FieldEnum.City.id()).stream().max(Comparator.comparing((x) -> x.getScores().get(2))).orElse(null));
//        if (!basicAddress.getAdministrationFields().get(FieldEnum.PostalCode.id()).isEmpty()) {
//            scoredAddress.setPostalCode(basicAddress.getAdministrationFields().get(FieldEnum.PostalCode.id()).get(0));
//        }
//        if (!basicAddress.getAdministrationFields().get(FieldEnum.StreetLine.id()).isEmpty()) {
//            scoredAddress.setStreetLine(basicAddress.getAdministrationFields().get(FieldEnum.StreetLine.id()).get(0));
//        }
//
////        scoredAddress.setCountry(basicAddress.getCountry().get(0));
////        scoredAddress.setState(basicAddress.getState().get(0));
////        scoredAddress.setCity(basicAddress.getCity().get(0));
////        scoredAddress.setPostalCode(basicAddress.getPostalCode().get(0));
////        scoredAddress.setStreetLine(basicAddress.getStreetLine().get(0));
//        return scoredAddress;
//    }



    private ScoredAddress scoreAddress(BasicAddress basicAddress) {
        StringSearch stringSearch = new StringSearch();
        Queue<ScoredAddress> scoredAddresses = new PriorityQueue<>(Comparator.comparing(ScoredAddress::getTotal).reversed());
        List<ScoredAdmUnit> scoredAdmUnitList = new ArrayList(basicAddress.getAdministrationFields().get(NO_UNIT_ADM_MAX - 1));
        for (ScoredAdmUnit scoredAdmUnit : scoredAdmUnitList) {
            AdministrativeUnit administrativeUnit = scoredAdmUnit.getAdministrativeUnit();
            ScoredAddress scoredAddress = new ScoredAddress();
            while (administrativeUnit.getSuperDivision() != null) {
                AdministrativeUnit superDivision = administrativeUnit.getSuperDivision();
                List<ScoredAdmUnit> scoredAdmUnits = new ArrayList<>(basicAddress.getAdministrationFields().get(superDivision.getLevel()));
                List<Double> distances = stringSearch.getDamerauLevenshteinDistances(superDivision.getParsedName(), scoredAdmUnits.stream().map((x) -> x.getAdministrativeUnit().getParsedName()).collect(Collectors.toList()));
                double minDistance = Integer.MAX_VALUE;
                for (Double distance : distances) {
                    if (distance < minDistance) {
                        minDistance = distance;
                    }
                }
//                 basicAddress.getFields().get(superDivision.getLevel()).get(max).addToScore(superDivision.getLevel(), (int) (50 - maxDistance * 10));
//                 basicAddress.getFields().get(currentAdministrativeUnit.getLevel()).get(admIndex).addToScore(currentAdministrativeUnit.getLevel(), (int) (50 - maxDistance * 10));

                scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), new ScoredAdmUnit(superDivision, scoredAdmUnit.getUniqueIdentifier()), (int) ((float) (1 / (minDistance + 1)) * 20));

                administrativeUnit = administrativeUnit.getSuperDivision();
            }
            scoredAddress.setScoreBasedOnLevel(scoredAdmUnit.getAdministrativeUnit().getLevel(), scoredAdmUnit, (int) (scoredAdmUnit.getScores().get(scoredAdmUnit.getAdministrativeUnit().getLevel()) + (1 / ((stringSearch.getDamerauLevenshteinDistances(scoredAdmUnit.getAdministrativeUnit().getParsedName(), basicAddress.getNameFields().get(scoredAdmUnit.getAdministrativeUnit().getLevel())).stream().min(Double::compareTo).orElse((double) Integer.MAX_VALUE)) + 1) * 20)));
            scoredAddress.computeTotal();
            scoredAddresses.add(scoredAddress);
        }
//        }
        return scoredAddresses.poll();
    }

    private ScoredAddress getBestScoredAddress(List<ScoredAddress> scoredAddresses) {
        ScoredAddress maxScoredAddress = new ScoredAddress();
        int maxScore = 0;
        for (ScoredAddress scoredAddress :
                scoredAddresses) {
            if (scoredAddress.getTotal() > maxScore) {
                maxScore = scoredAddress.getTotal();
                maxScoredAddress = scoredAddress;
            }
        }
        return maxScoredAddress;
    }

}
