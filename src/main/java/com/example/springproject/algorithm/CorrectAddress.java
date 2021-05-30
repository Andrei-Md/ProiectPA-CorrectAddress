package com.example.springproject.algorithm;

import com.example.springproject.algorithm.Scoring.AddressScoring;
import com.example.springproject.algorithm.Scoring.BasicScoring;
import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAddress;
import com.example.springproject.algorithm.parser.AddressParser;
import com.example.springproject.algorithm.parser.ParserInterface;
import com.example.springproject.model.Address;
import com.example.springproject.structures.AdmStructures;
import com.example.springproject.structures.entities.AdministrativeHierarchy;

import java.util.ArrayList;
import java.util.List;

public class CorrectAddress {
    private BasicAddress basicAddress;

//    public CorrectAddress(){
//
//    }

    public List<Address> correctAddress(Address address) {
        AdministrativeHierarchy administrativeHierarchy = AdmStructures.getAdministrativeHierarchy();
        this.basicAddress = parseAddress(address);
        List<ScoredAddress> scoredAddressesList;
        scoredAddressesList = scoringAddress(this.basicAddress);

        List<Address> correctedAddressList = convertScoredAdressToAdress(scoredAddressesList);
        return correctedAddressList;
    }

    private BasicAddress parseAddress(Address addressList) {
        ParserInterface parserInterface = new AddressParser();
        return parserInterface.parseAddress(addressList);
    }

    private List<ScoredAddress> scoringAddress(BasicAddress basicAddress) {
        AddressScoring addressScoring = new BasicScoring();

        return addressScoring.addressScoring(basicAddress);
    }

    private List<Address> convertScoredAdressToAdress(List<ScoredAddress> scoredAddressList) {
        List<Address> addressList = new ArrayList<>();
        for (ScoredAddress scoredAddress : scoredAddressList) {
            Address address = new Address(scoredAddress.getCountry().getAdministrativeUnit().getName(),
                    scoredAddress.getState().getAdministrativeUnit().getName(),
                    scoredAddress.getCity().getAdministrativeUnit().getName(),
                    scoredAddress.getStreetLine().getAdministrativeUnit().getName(),
                    scoredAddress.getPostalCode().getAdministrativeUnit().getName());
            addressList.add(address);
        }
        return addressList;
    }

}
