package com.example.springproject.algorithm.scoring;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.search.stringSearch.StringSearch;
import com.example.springproject.algorithm.util.Pair;
import com.example.springproject.structures.entities.AdministrativeUnit;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.springproject.algorithm.util.ScoreUtil.*;
import static java.lang.Math.max;

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
//        for (int i = NO_UNIT_ADM_MAX - 1; i >= 0; i--) {
        for (ScoredAdmUnit scoredAdmUnit : administrationFields.get(NO_UNIT_ADM_MAX - 1)) {
            AdministrativeUnit administrativeUnit = scoredAdmUnit.getAdministrativeUnit();
            ScoredAddress scoredAddress = new ScoredAddress();
            int bonus = 0;


            Pair<Integer, Integer> bonusPair = computeBonusBasedOnDistances(basicAddress.getAllAdmUnitNames(), scoredAdmUnit.getAdministrativeUnit().getParsedName(), BONUS_INTERCROSSING_POSITION, BONUS_INTERCROSSING_POSITION_INCREMENT);
            bonus = bonusPair.getKey();
            bonus = applyBonusPenalty(bonus, scoredAddress, scoredAdmUnit.getUniqueIdentifier());
            if(scoredAdmUnit.getScores().get(NO_UNIT_ADM_MAX - 1) == 20){
                bonus = (int) (bonus * 0.25);
            }
            scoredAdmUnit.addToScore(NO_UNIT_ADM_MAX - 1, bonus);
            scoredAddress.setScoreBasedOnLevel(scoredAdmUnit.getAdministrativeUnit().getLevel(), scoredAdmUnit, (scoredAdmUnit.getScores().get(scoredAdmUnit.getAdministrativeUnit().getLevel())));

            while (administrativeUnit.getSuperDivision() != null) {
                AdministrativeUnit superDivision = administrativeUnit.getSuperDivision();
                List<ScoredAdmUnit> scoredAdmUnits = new ArrayList<>(basicAddress.getAdministrationFields().get(superDivision.getLevel()));
                bonusPair = computeBonusBasedOnDistances(scoredAdmUnits.stream().map((x) ->
                        x.getAdministrativeUnit().getAsciiName()).collect(Collectors.toList()), superDivision.getAsciiName(), BONUS_SAME_POSITION, BONUS_SAME_POSITION_INCREMENT);
                bonus = bonusPair.getKey();
                int index = bonusPair.getValue();
                applyBonus(administrationFields, scoredAdmUnit, scoredAddress, bonus, superDivision, index);
                administrativeUnit = administrativeUnit.getSuperDivision();
            }
            scoredAddress.computeTotal();
            scoredAddresses.add(scoredAddress);
            }
//        }
        return getBestScoredAddress(scoredAddresses);

    }

    /**
     * method to apply the bonus on a scoredAddress
     * @param administrationFields the list of administration fields
     * @param scoredAdmUnit the current scoredAdmUnit
     * @param scoredAddress the current scoredAddress
     * @param bonus the max bonus that can be applied
     * @param superDivision the super division of the actual scored adm unit
     */
    private void applyBonus(List<List<ScoredAdmUnit>> administrationFields, ScoredAdmUnit scoredAdmUnit, ScoredAddress scoredAddress, int bonus, AdministrativeUnit superDivision, int index) {
        if (bonus == BONUS_SAME_POSITION) {
            bonus = applyBonusPenalty(bonus, scoredAddress, administrationFields.get(superDivision.getLevel()).get(index).getUniqueIdentifier());
            ScoredAdmUnit newScoredAdmUnit =  new ScoredAdmUnit(superDivision, administrationFields.get(superDivision.getLevel()).get(index).getUniqueIdentifier());
            newScoredAdmUnit.setScores(superDivision.getLevel(), administrationFields.get(superDivision.getLevel()).get(index).getScores().get(index) + bonus);
            scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), newScoredAdmUnit, administrationFields.get(superDivision.getLevel()).get(index).getScores().get(index) + bonus);
        } else {
            bonus = applyBonusPenalty(bonus, scoredAddress, scoredAdmUnit.getUniqueIdentifier());
            ScoredAdmUnit newScoredAdmUnit = new ScoredAdmUnit(superDivision, scoredAdmUnit.getUniqueIdentifier());
            newScoredAdmUnit.setScores(superDivision.getLevel(), bonus);
            scoredAddress.setScoreBasedOnLevel(superDivision.getLevel(), newScoredAdmUnit, bonus);
        }
    }

    /**
     * method that computes a bonus based on Damerau Levenshtein Distances
     *
     * @param wordList   the list of words for the levenshtein distances
     * @param wordToFind the word to use for the levenshtein distances
     * @param maxBonus   the max bonus that can be given
     * @return a pair that consists of the bonus and the index of the most close word
     * according to the Damerau Levenshtein Distances algorithm
     */
    private Pair<Integer, Integer> computeBonusBasedOnDistances(List<String> wordList, String wordToFind, int maxBonus, int bonusIncrement) {
        StringSearch stringSearch = new StringSearch();
        int bonus = 0;
        int minDistance = 0;
        List<Double> distances = stringSearch.getDamerauLevenshteinDistances(wordToFind, wordList);
        if (distances.size() != 0) {
            minDistance = getMinDistanceIndex(distances);
            bonus = computeBonus(distances.get(minDistance), maxBonus, bonusIncrement);
        }
        return new Pair<>(bonus, minDistance);
    }

    private int computeBonus(double DLdistance, int maxBonus, int bonusIncrement) {
        return (int) max((maxBonus - DLdistance * bonusIncrement), 0);
    }

    /**
     * method that applies a bonus penalty in case a administration unit with
     * the same unique identifier was already added to the scored address
     *
     * @param bonus            the bonus
     * @param scoredAddress    the scored address
     * @param uniqueIdentifier the unique identifier
     * @return the new bonus
     */
    private int applyBonusPenalty(int bonus, ScoredAddress scoredAddress, int uniqueIdentifier) {
        if (uniqueIdentifierExists(scoredAddress, uniqueIdentifier)) {
            return (int) (bonus * 0.5);
        }
        return bonus;
    }


    /**
     * method to check if a scored address already contains an administration unit with
     * an unique identifier
     *
     * @param scoredAddress    the scored address
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
     *
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
     *
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
