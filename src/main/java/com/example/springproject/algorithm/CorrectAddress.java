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

/**
 * class that encapsulate the corrected address
 */

public class CorrectAddress {
    private BasicAddress basicAddress;

//    public CorrectAddress(){
//
//    }

    /**
     * main method to correct the address
     * @param address the address to correct
     * @return a corrected address list
     */
    public List<Address> correctAddress(Address address) {
        AdministrativeHierarchy administrativeHierarchy = AdmStructures.getAdministrativeHierarchy();
        this.basicAddress = parseAddress(address);
        List<ScoredAddress> scoredAddressesList;
        scoredAddressesList = scoringAddress(this.basicAddress);

        List<Address> correctedAddressList = convertScoredAddressToAddress(scoredAddressesList);
        return correctedAddressList;
    }

    /**
     * method used to parse the address and transform it into a basic address
     * @param addressList the address list
     * @return a basic address
     */
    private BasicAddress parseAddress(Address addressList) {
        ParserInterface parserInterface = new AddressParser();
        return parserInterface.parseAddress(addressList);
    }

    /**
     * method used to score the basic address
     * @param basicAddress basic address
     * @return list of scored address
     */
    private List<ScoredAddress> scoringAddress(BasicAddress basicAddress) {
        AddressScoring addressScoring = new BasicScoring();

        return addressScoring.addressScoring(basicAddress);
    }

    /**
     * method used to convert scored address to address
     * @param scoredAddressList scored address list
     * @return list of address
     */
    private List<Address> convertScoredAddressToAddress(List<ScoredAddress> scoredAddressList) {
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

    /**
     * method used to get the name of the scored administrative unit
     * @param scoredAdmUnit scored administrative unit
     * @return string representing the administrative unit name
     */
    private String scoredAddressGetAdmUnitName(ScoredAdmUnit scoredAdmUnit){
        if(scoredAdmUnit == null){
            return "";
        }
        return admUnitGetName(scoredAdmUnit.getAdministrativeUnit());
    }

    /**
     * method to get the name of the administrative unit
     * @param administrativeUnit administrative unit
     * @return the administrative unit name
     */
    private String admUnitGetName(AdministrativeUnit administrativeUnit) {

        if (administrativeUnit == null) {
            return "";
        }
        return administrativeUnit.getName();
    }

}
