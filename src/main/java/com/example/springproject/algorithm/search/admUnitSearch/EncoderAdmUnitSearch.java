package com.example.springproject.algorithm.search.admUnitSearch;

import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.structures.entities.AdministrativeUnit;
import com.google.common.collect.SetMultimap;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

import java.util.ArrayList;
import java.util.List;

import static com.example.springproject.algorithm.util.ScoreUtil.*;

/**
 * class that encapsulate the encoding algorithm
 */
public class EncoderAdmUnitSearch implements AdmUnitSearchInterface {

    /**
     * method used to search the encoded received string from the encoded administrative units list of maps
     * and return a list of administrative units
     *
     * @param stringEncoder       the string encoder
     * @param encodedUnitsMapList encoded administrative units list of maps
     * @param entityName          entity name - the actual received name
     * @param id                  the id of the unit so it can be applied a bonus if something is found on same position
     * @param bonus               bonus for the administrative unit
     * @return
     */
    @Override
    public List<List<ScoredAdmUnit>> search(StringEncoder stringEncoder, List<SetMultimap<String, AdministrativeUnit>> encodedUnitsMapList, String entityName, int id, int bonus) {
        incrementUniqueAdmUnitIdentifier();
        List<List<ScoredAdmUnit>> returnedScoredAdmUnitList = new ArrayList<>();
        String encodedName = null;
        try {
            encodedName = stringEncoder.encode(entityName);
        } catch (EncoderException | IllegalArgumentException e) {
            e.printStackTrace();
            return returnedScoredAdmUnitList;
        }

        for (int i = 0; i < NO_UNIT_ADM_MAX; i++) {
            List<ScoredAdmUnit> scoredAdmUnitList = new ArrayList<>();
            //check if key exists
            if (encodedUnitsMapList.get(i).containsKey(encodedName)) {
                for (AdministrativeUnit admUnit : encodedUnitsMapList.get(i).get(encodedName)) {
                    ScoredAdmUnit scoredAdmUnit;
                    if (i == id) {
                        scoredAdmUnit = transformAdmUnitToScoredAdmUnit(admUnit, UNIQUE_ADMUNIT_IDENTIFIER, id, bonus);
                    } else {
                        scoredAdmUnit = transformAdmUnitToScoredAdmUnit(admUnit, UNIQUE_ADMUNIT_IDENTIFIER);
                    }
                    scoredAdmUnitList.add(scoredAdmUnit);
                }
            }
            returnedScoredAdmUnitList.add(scoredAdmUnitList);
        }
        return returnedScoredAdmUnitList;
    }


}
