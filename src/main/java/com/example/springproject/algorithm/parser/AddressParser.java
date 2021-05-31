package com.example.springproject.algorithm.parser;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.search.FuzzySearch;
import com.example.springproject.algorithm.search.SearchInterface;
import com.example.springproject.model.Address;
import com.example.springproject.structures.entities.AdministrativeUnit;

import java.util.List;

public class AddressParser implements ParserInterface {

    private BasicAddress basicAddress;

    public AddressParser(){
        basicAddress = new BasicAddress();
    }

    @Override
    public BasicAddress parseAddress(Address address) {
        address.setCountry("Parser");
        this.basicAddress = parse(address);

        return basicAddress;
    }

    /**
     * parse each string and transform it in Scored Administrative Unit
     * @param address
     * @return
     */
    private BasicAddress parse(Address address) {
        //parse TODO

        this.basicAddress.addCountryAll(searchAdmUnit(address.getCountry()));
        this.basicAddress.addCityAll(searchAdmUnit(address.getCity()));
        this.basicAddress.addStateAll(searchAdmUnit(address.getState()));
        this.basicAddress.addPostalCodeAll(searchAdmUnit(address.getPostalCode()));
        this.basicAddress.addStreetLineAll(searchAdmUnit(address.getStreetLine()));
        this.basicAddress.addUnknownAll(searchAdmUnit(address.getCountry()));

        return this.basicAddress;
    }

    /**
     * TODO transform in new class
     */
    private List<ScoredAdmUnit> searchAdmUnit(String entity){
        SearchInterface searchInterface = new FuzzySearch();


//        String[] row = {"686579","Comuna Albac","Comuna Albac","","46.45246","22.95027","RO"};
//        return new ScoredAdmUnit(new AdministrativeUnit(row,","),0);
        return searchInterface.search(entity);
    }
}
