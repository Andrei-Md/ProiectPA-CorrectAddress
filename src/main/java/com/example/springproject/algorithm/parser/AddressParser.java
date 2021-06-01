package com.example.springproject.algorithm.parser;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.FieldEnum;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.search.admUnitSearch.AdmUnitSearchInterface;
import com.example.springproject.algorithm.search.admUnitSearch.SoundexAdmUnitSearch;
import com.example.springproject.model.Address;

import java.util.ArrayList;
import java.util.List;

import static com.example.springproject.algorithm.ScoreUtil.*;

public class AddressParser implements ParserInterface {

    private BasicAddress basicAddress;
    private List<String> namesList;

    public AddressParser() {
        basicAddress = new BasicAddress();
    }

    @Override
    public BasicAddress parseAddress(Address address) {
//        address.setCountry("Parser");
        this.basicAddress = parse(address);

        this.basicAddress = searchEntity(basicAddress);
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

        //treat administrative units
        this.basicAddress.addNameField(address.getCountry(),FieldEnum.Country.id());
        this.basicAddress.addNameField(address.getState(),FieldEnum.State.id());
        this.basicAddress.addNameField(address.getCity(),FieldEnum.City.id());

        //treat unknown
        this.basicAddress.addNameField(address.getStreetLine(),FieldEnum.Unknown.id());
        this.basicAddress.addNameField(address.getPostalCode(),FieldEnum.Unknown.id());

        //add all String to a list
        this.basicAddress.setAllAdmUnitNames(createAllNamesList(this.basicAddress.getNameFields()));
        //fill nameList
        return this.basicAddress;
    }

    /**
     * method to create a list of all Names
     * @param nameFields - list of list of administrative unit names
     * @return one list of all administrative unit names
     */
    private List<String> createAllNamesList(List<List<String>> nameFields) {
        List<String> allNames = new ArrayList<>();
        //add administrative Unis names
        for (int i = 0; i < NO_UNIT_ADM_MAX; i++){
            allNames.addAll(nameFields.get(i));
        }
        //add unknown names
        allNames.addAll(nameFields.get(FieldEnum.Unknown.id()));

        return allNames;
    }

    /**
     * method used to search the name Fields into the Administrative Unit Map
     *
     * @param basicAddress
     * @return
     */
    private BasicAddress searchEntity(BasicAddress basicAddress) {
        //Sondex
        //for each administrative unit
        for (int i = 0; i < NO_UNIT_ADM_MAX; i++) {
            for (String admUnitName : basicAddress.getNameFields().get(i)) {
                this.basicAddress.addAllAdmUnit(searchAdmUnitSoundex(admUnitName, i));
            }
        }
        //treat unknown cases
        for (String admUnitName : basicAddress.getNameFields().get(FieldEnum.Unknown.id())) {
            this.basicAddress.addAllAdmUnit(searchAdmUnitSoundex(admUnitName, MAX_NO_FIELD)); //Max_no_field to be sure no bonus is added
        }
        return this.basicAddress;
    }

    /**
     * method used to search the string for administrative unit and return directly the scoredAdministrative unit
     *
     * @param entity the unit Name
     * @param id     the id of the unit so it can be applied a bonus if something is found on same position
     * @return the list of multimap representing the similar administrative units found on diferents fields (Country, City, State)
     */
    private List<List<ScoredAdmUnit>> searchAdmUnitSoundex(String entity, int id) {
        AdmUnitSearchInterface admUnitSearchInterface = new SoundexAdmUnitSearch();
        return admUnitSearchInterface.search(entity, id, BONUS_SAME_POSITION);
    }
}
