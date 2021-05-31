package com.example.springproject.algorithm.search.admUnitSearch;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.structures.AdmStructures;
import com.example.springproject.structures.entities.AdministrativeUnit;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.commons.codec.language.Soundex;

import java.util.ArrayList;
import java.util.List;

import static com.example.springproject.algorithm.ScoreUtil.*;

public class SoundexAdmUnitSearch implements AdmUnitSearchInterface {

    private Soundex soundex = new Soundex();
    private List<BasicAddress> addressList = new ArrayList<>();

    @Override
    public List<List<ScoredAdmUnit>> search(String entityName, int id, int bonus) {
        incrementUniqueAdmUnitIdentifier();
        String encodedName = this.soundex.encode(entityName);
        List<List<ScoredAdmUnit>> returnedScoredAdmUnitList = new ArrayList<>();

        for (int i = 0; i < NO_UNIT_ADM_MAX; i++) {
            List<ScoredAdmUnit> scoredAdmUnitList = new ArrayList<>();
            //check if key exists
            if (AdmStructures.getAdministrativeHierarchy().getSoundexUnitsMapList().get(i).containsKey(encodedName)) {
                for (AdministrativeUnit admUnit : AdmStructures.getAdministrativeHierarchy().getSoundexUnitsMapList().get(i).get(encodedName)) {
                    ScoredAdmUnit scoredAdmUnit;
                    if (i == id) {
                        scoredAdmUnit = transformAdmUnitToScoredAdmUnit(admUnit, UNIQUE_ADMUNIT_IDENTIFIER ,id, bonus);
                    } else {
                        scoredAdmUnit = transformAdmUnitToScoredAdmUnit(admUnit, UNIQUE_ADMUNIT_IDENTIFIER);
                    }
                    scoredAdmUnitList.add(scoredAdmUnit);
                }
            }
            returnedScoredAdmUnitList.add(scoredAdmUnitList);
        }
//        String[] row = {"686579", "Comuna Alb", "Comuna Alb", "", "46.45246", "22.95027", "RO"};
//        ScoredAdmUnit scoredAdmUnit = new ScoredAdmUnit(new AdministrativeUnit(row, ","), 0);
//        scoredAdmUnitList.add(scoredAdmUnit);
        return returnedScoredAdmUnitList;
    }


}
