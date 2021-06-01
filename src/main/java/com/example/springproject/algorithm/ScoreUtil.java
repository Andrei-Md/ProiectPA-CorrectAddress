package com.example.springproject.algorithm;

import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.structures.entities.AdministrativeUnit;

public class ScoreUtil {
    public static final int BONUS_SAME_POSITION = 20;
    public static final int BONUS_INTERCROSSING_POSITION = 15;
    public static int UNIQUE_ADMUNIT_IDENTIFIER = 0;
    public final static int NO_UNIT_ADM_MAX = 3;
    public static final int MAX_NO_FIELD = 6;

    public static ScoredAdmUnit transformAdmUnitToScoredAdmUnit(AdministrativeUnit admUnit, int uniqueIdentifier) {
        return new ScoredAdmUnit(admUnit,uniqueIdentifier);
    }

    public static ScoredAdmUnit transformAdmUnitToScoredAdmUnit(AdministrativeUnit admUnit,int uniqueIdentifier ,int id, int bonus) {
        ScoredAdmUnit scoredAdmUnit = new ScoredAdmUnit(admUnit,uniqueIdentifier);
        scoredAdmUnit.setScores(id,bonus);
        return scoredAdmUnit;
    }

    /**
     * method used to increment UNIQUE_ADMUNIT_IDENTIFIER
     */
    public static void incrementUniqueAdmUnitIdentifier(){
        UNIQUE_ADMUNIT_IDENTIFIER++;
    }
}
