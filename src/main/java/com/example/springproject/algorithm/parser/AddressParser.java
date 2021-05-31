package com.example.springproject.algorithm.parser;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.FieldEnum;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.search.admUnitSearch.AdmUnitSearchInterface;
import com.example.springproject.algorithm.search.admUnitSearch.SoundexAdmUnitSearch;
import com.example.springproject.model.Address;

import java.util.List;

import static com.example.springproject.algorithm.ScoreUtil.BONUS_SAME_POSITION;

public class AddressParser implements ParserInterface {

    private BasicAddress basicAddress;


    public AddressParser() {
        basicAddress = new BasicAddress();
    }

    @Override
    public BasicAddress parseAddress(Address address) {
//        address.setCountry("Parser");
        this.basicAddress = parse(address);

        return basicAddress;
    }

    /**
     * parse each string and transform it in Scored Administrative Unit
     *
     * @param address
     * @return
     */
    private BasicAddress parse(Address address) {
        //parse TODO
        //also have an unknown field for locations which are parsed in streeline field or postal code field

//        this.basicAddress.addCountryAll(searchAdmUnit(address.getCountry()));
//        this.basicAddress.addCityAll(searchAdmUnit(address.getCity()));
//        this.basicAddress.addStateAll(searchAdmUnit(address.getState()));
//        this.basicAddress.addPostalCodeAll(searchAdmUnit(address.getPostalCode()));
//        this.basicAddress.addStreetLineAll(searchAdmUnit(address.getStreetLine()));
//        this.basicAddress.addUnknownAll(searchAdmUnit(address.getCountry()));
        basicAddress.addAllAdmUnit(searchAdmUnit(address.getCountry(), FieldEnum.Country.id()));
        basicAddress.addAllAdmUnit(searchAdmUnit(address.getCity(), FieldEnum.City.id()));
        basicAddress.addAllAdmUnit(searchAdmUnit(address.getState(), FieldEnum.State.id()));
//        basicAddress.addAll(searchAdmUnit(address.getPostalCode(), Score.PostalCode.id()));
//        basicAddress.addAll(searchAdmUnit(address.getStreetLine(), Score.StreetLine.id()));

        return this.basicAddress;
    }

    /**
     * method used to search the string for administrative unit and return directly the scoredAdministrative unit
     *
     * @param entity the unit Name
     * @param id     the id of the unit so it can be applied a bonus if something is found on same position
     * @return the list of multimap representing the similar administrative units found on diferents fields (Country, City, State)
     */
    private List<List<ScoredAdmUnit>> searchAdmUnit(String entity, int id) {
        AdmUnitSearchInterface admUnitSearchInterface = new SoundexAdmUnitSearch();
        return admUnitSearchInterface.search(entity, id, BONUS_SAME_POSITION);
    }
}
