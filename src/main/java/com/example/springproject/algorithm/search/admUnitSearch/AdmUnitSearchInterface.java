package com.example.springproject.algorithm.search.admUnitSearch;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.structures.entities.AdministrativeUnit;
import com.google.common.collect.SetMultimap;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

import java.util.List;

public interface AdmUnitSearchInterface {

    public List<List<ScoredAdmUnit>> search(StringEncoder stringEncoder, List<SetMultimap<String, AdministrativeUnit>> encodedUnitsMapList, String entity, int id, int bonus);
}
