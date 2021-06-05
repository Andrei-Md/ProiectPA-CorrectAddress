package com.example.springproject.algorithm.scoring;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.scoring.AddressScoring;
import com.example.springproject.algorithm.search.stringSearch.StringSearch;
import com.example.springproject.structures.AdmStructures;
import com.example.springproject.structures.entities.AdministrativeHierarchy;
import com.example.springproject.structures.entities.AdministrativeUnit;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.springproject.algorithm.ScoreUtil.*;

public class BasicScoring implements AddressScoring {

    @Override
    public List<ScoredAddress> addressScoring(BasicAddress basicAddress) {
        return scoreAddress(basicAddress);
    }

    /**
     * method that returns a list of the best scored addresses
     *
     * @param basicAddress a basic address
     * @return a list of scored addresses
     */
    private List<ScoredAddress> scoreAddress(BasicAddress basicAddress) {
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
                Pair<Integer, Integer> bonusPair = computeBonusBasedOnDistances(scoredAdmUnit, scoredAddress, scoredAdmUnits.stream().map((x) -> x.getAdministrativeUnit().getAsciiName()).collect(Collectors.toList()), superDivision.getAsciiName(), BONUS_SAME_POSITION);
                int bonus = bonusPair.getKey();
                int index = bonusPair.getValue();
                if (bonus != Double.MAX_VALUE) {
                    if (bonus == BONUS_SAME_POSITION) {
                        scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), new ScoredAdmUnit(superDivision, administrationFields.get(superDivision.getLevel()).get(index).getUniqueIdentifier()), +bonus);
                    } else {
                        scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), new ScoredAdmUnit(superDivision, scoredAdmUnit.getUniqueIdentifier()), bonus);
                    }
                } else {
                    scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), new ScoredAdmUnit(superDivision, scoredAdmUnit.getUniqueIdentifier()), 0);
                }
                administrativeUnit = administrativeUnit.getSuperDivision();
            }
            int bonus = computeBonusBasedOnDistances(scoredAdmUnit, scoredAddress, basicAddress.getAllAdmUnitNames(), scoredAdmUnit.getAdministrativeUnit().getParsedName(), BONUS_INTERCROSSING_POSITION).getKey();
            scoredAddress.setScoreBasedOnLevel(scoredAdmUnit.getAdministrativeUnit().getLevel(), scoredAdmUnit, (scoredAdmUnit.getScores().get(scoredAdmUnit.getAdministrativeUnit().getLevel()) + bonus));
            scoredAddress.computeTotal();
            scoredAddresses.add(scoredAddress);
        }
        return getBestScoredAddress(scoredAddresses);

    }

    /**
     * method that computes a bonus based on Damerau Levenshtein Distances
     *
     * @param scoredAdmUnit the current ScoredAdmUnit
     * @param scoredAddress the scored address
     * @param wordList      the list of words for the levenshtein distances
     * @param wordToFind    the word to use for the levenshtein distances
     * @param maxBonus      the max bonus that can be given
     * @return a pair that consists of the bonus and the index of the most close word
     * according to the Damerau Levenshtein Distances algorithm
     */
    private Pair<Integer, Integer> computeBonusBasedOnDistances(ScoredAdmUnit scoredAdmUnit, ScoredAddress scoredAddress, List<String> wordList, String wordToFind, int maxBonus) {
        StringSearch stringSearch = new StringSearch();
        int bonus = 0;
        int minDistance = 0;
        List<Double> distances = stringSearch.getDamerauLevenshteinDistances(wordToFind, wordList);
        if (distances.size() != 0) {
            minDistance = getMinDistanceIndex(distances);
            if (distances.get(minDistance) >= 5) {
                distances.set(minDistance, Double.MAX_VALUE);
            } else {
                //TODO max(BONUS_SAME_POSITION - DL*(BONUS_SAME_POSITION_INCREMENT,0)
                if (uniqueIdentifierExists(scoredAddress, scoredAdmUnit.getUniqueIdentifier())) {
                    bonus = (int) ((float) (1 / (distances.get(minDistance) + 1)) * maxBonus);
                    bonus = (int) (bonus * 0.5);
                } else {
                    bonus = (int) ((float) (1 / (distances.get(minDistance) + 1)) * maxBonus);
                }
            }
        }
        return new Pair<>(bonus, minDistance);
    }

    /**
     * method to check if a scored address already contains an administration unit with
     * an unique identifier
     * @param scoredAddress the scored address
     * @param uniqueIdentifier the unique identifier
     * @return true if the scored address already contains an entity with this unique identifier, false otherwise
     */
    private boolean uniqueIdentifierExists(ScoredAddress scoredAddress, int uniqueIdentifier) {
        if (scoredAddress.getCity() != null && scoredAddress.getCity().getUniqueIdentifier() == uniqueIdentifier)
            return true;
        if (scoredAddress.getCountry() != null && scoredAddress.getCountry().getUniqueIdentifier() == uniqueIdentifier)
            return true;
        return scoredAddress.getState() != null && scoredAddress.getState().getUniqueIdentifier() == uniqueIdentifier;
    }

    /**
     * method to get the index for the min distance from a list of distances
     * @param distances a list of distances
     * @return the index of the min distance
     */
    private int getMinDistanceIndex(List<Double> distances) {
        int index = 0;
        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) < distances.get(index)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * method that gets the best scored addresses from a queue
     * @param scoredAddresses a queue of scored addresses
     * @return a list of the best scored addresses (all with the same total score)
     */
    private List<ScoredAddress> getBestScoredAddress(Queue<ScoredAddress> scoredAddresses) {
        List<ScoredAddress> maxScoredAddress = new ArrayList<>();
        if (scoredAddresses.isEmpty()) {
            return maxScoredAddress;
        }
        ScoredAddress scoredAddress;
        maxScoredAddress.add(scoredAddresses.poll());
        int maxScore = maxScoredAddress.get(0).getTotal();
        do {
            if ((scoredAddress = scoredAddresses.poll()) != null && maxScore == scoredAddress.getTotal()) {
                maxScoredAddress.add(scoredAddress);
            } else {
                break;
            }
        } while (true);
        return maxScoredAddress;
    }

}
