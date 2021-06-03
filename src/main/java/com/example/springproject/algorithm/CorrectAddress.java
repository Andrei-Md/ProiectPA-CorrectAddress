package com.example.springproject.algorithm;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAddress;
import com.example.springproject.algorithm.model.ScoredAdmUnit;
import com.example.springproject.algorithm.parser.AddressParser;
import com.example.springproject.algorithm.parser.ParserInterface;
import com.example.springproject.algorithm.scoring.AddressScoring;
import com.example.springproject.algorithm.scoring.BasicScoring;
import com.example.springproject.model.Address;
import com.example.springproject.structures.AdmStructures;
import com.example.springproject.structures.entities.AdministrativeHierarchy;
import com.example.springproject.structures.entities.AdministrativeUnit;

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
            Address address = new Address(scoredAddressGetAdmUnitName(scoredAddress.getCountry()),
                    scoredAddressGetAdmUnitName(scoredAddress.getState()),
                    scoredAddressGetAdmUnitName(scoredAddress.getCity()),
                    scoredAddressGetAdmUnitName(scoredAddress.getStreetLine()),
                    scoredAddressGetAdmUnitName(scoredAddress.getPostalCode()));
            addressList.add(address);
        }
        return addressList;
    }

    private String scoredAddressGetAdmUnitName(ScoredAdmUnit scoredAdmUnit){
        if(scoredAdmUnit == null){
            return "";
        }
        return admUnitGetName(scoredAdmUnit.getAdministrativeUnit());
    }


    private String admUnitGetName(AdministrativeUnit administrativeUnit) {

        if (administrativeUnit == null) {
            return "";
        }
        return administrativeUnit.getName(); //TODO maybe get ASCII name
    }

}
