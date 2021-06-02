package com.example.springproject.algorithm.Scoring;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.FieldEnum;
import com.example.springproject.algorithm.model.ScoredAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.search.stringSearch.StringSearch;
import com.example.springproject.structures.AdmStructures;
import com.example.springproject.structures.entities.AdministrativeHierarchy;
import com.example.springproject.structures.entities.AdministrativeUnit;
import org.apache.commons.collections.ArrayStack;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.springproject.algorithm.ScoreUtil.*;

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
        List<List<ScoredAdmUnit>> administrationFields = new ArrayList<>();
        for (int i = 0; i < NO_UNIT_ADM_MAX; i++) {
            administrationFields.add(new ArrayList<>(basicAddress.getAdministrationFields().get(i)));
        }
        for (ScoredAdmUnit scoredAdmUnit : administrationFields.get(NO_UNIT_ADM_MAX - 1)) {
            AdministrativeUnit administrativeUnit = scoredAdmUnit.getAdministrativeUnit();
            ScoredAddress scoredAddress = new ScoredAddress();
            while (administrativeUnit.getSuperDivision() != null) {
                AdministrativeUnit superDivision = administrativeUnit.getSuperDivision();
                List<ScoredAdmUnit> scoredAdmUnits = new ArrayList<>(basicAddress.getAdministrationFields().get(superDivision.getLevel()));
                List<Double> distances = stringSearch.getDamerauLevenshteinDistances(superDivision.getAsciiName(), scoredAdmUnits.stream().map((x) -> x.getAdministrativeUnit().getAsciiName()).collect(Collectors.toList()));
                int minDistance = Integer.MAX_VALUE;
                if (distances.size() != 0) {
                    minDistance = getMinDistance(distances);
                    int bonus = 0;
                    if(uniqueIdentifierExists(scoredAddress,administrationFields.get(superDivision.getLevel()).get(minDistance).getUniqueIdentifier())){
                        bonus = (int) ((float) (1 / (distances.get(minDistance) + 1)) * BONUS_SAME_POSITION) - 5;
                    } else{
                        bonus = (int) ((float) (1 / (distances.get(minDistance) + 1)) * BONUS_SAME_POSITION);
                    }
                    if (distances.get(minDistance) == 0) {
                        scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), new ScoredAdmUnit(superDivision, administrationFields.get(superDivision.getLevel()).get(minDistance).getUniqueIdentifier()), administrationFields.get(superDivision.getLevel()).get(minDistance).getScores().get(superDivision.getLevel()) + bonus);
                    } else {
                        scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), new ScoredAdmUnit(superDivision, scoredAdmUnit.getUniqueIdentifier()), bonus);
                    }
                } else {
                    scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), new ScoredAdmUnit(superDivision, scoredAdmUnit.getUniqueIdentifier()), (int) ((float) (1 / (minDistance + 1)) * BONUS_SAME_POSITION));
                }
                administrativeUnit = administrativeUnit.getSuperDivision();
            }
            List<Double> distances = stringSearch.getDamerauLevenshteinDistances(scoredAdmUnit.getAdministrativeUnit().getParsedName(), basicAddress.getAllAdmUnitNames());
            int minDistance = getMinDistance(distances);
            int bonus;
            if(uniqueIdentifierExists(scoredAddress,scoredAdmUnit.getUniqueIdentifier())){
                bonus = (int) ((float) (1 / (distances.get(minDistance) + 1)) * BONUS_SAME_POSITION) - 5;
            } else{
                bonus = (int) ((float) (1 / (distances.get(minDistance) + 1)) * BONUS_SAME_POSITION);
            }
//            if(scoredAdmUnit.getUniqueIdentifier() == administrationFields.get(scoredAdmUnit.getAdministrativeUnit().getLevel()).get(minDistance).getUniqueIdentifier()){
//                scoredAddress.setScoreBasedOnLevel(scoredAdmUnit.getAdministrativeUnit().getLevel(), scoredAdmUnit, (scoredAdmUnit.getScores().get(scoredAdmUnit.getAdministrativeUnit().getLevel()) + (1 / (minDistance + 1) * BONUS_INTERCROSSING_POSITION - 10)));
//            } else{
            scoredAddress.setScoreBasedOnLevel(scoredAdmUnit.getAdministrativeUnit().getLevel(), scoredAdmUnit, (scoredAdmUnit.getScores().get(scoredAdmUnit.getAdministrativeUnit().getLevel()) + bonus));
//            }
//            scoredAddress.setScoreBasedOnLevel(scoredAdmUnit.getAdministrativeUnit().getLevel(), scoredAdmUnit,
//                    (int) (scoredAddress.getScoreBasedOnLevel(scoredAdmUnit.getAdministrativeUnit().getLevel()) +
//                            (1 / ((stringSearch.getDamerauLevenshteinDistances(scoredAdmUnit.getAdministrativeUnit().getParsedName(), basicAddress.getNameFields().get(scoredAdmUnit.getAdministrativeUnit().getLevel())).stream().min(Double::compareTo).orElse(Double.MAX_VALUE)) + 1) * BONUS_SAME_POSITION)));
            scoredAddress.computeTotal();
            scoredAddresses.add(scoredAddress);
        }
//        }
        return scoredAddresses.poll();
    }

    private boolean uniqueIdentifierExists(ScoredAddress scoredAddress, int uniqueIdentifier) {
        if(scoredAddress.getCity() != null && scoredAddress.getCity().getUniqueIdentifier() == uniqueIdentifier)
            return true;
        if(scoredAddress.getCountry() != null && scoredAddress.getCountry().getUniqueIdentifier() == uniqueIdentifier)
            return true;
        if(scoredAddress.getState() != null && scoredAddress.getState().getUniqueIdentifier() == uniqueIdentifier)
            return true;
        return false;
    }

    private int getMinDistance(List<Double> distances) {
        int minDistance = 0;
        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) < distances.get(minDistance)) {
                minDistance = i;
            }
        }
        return minDistance;
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
