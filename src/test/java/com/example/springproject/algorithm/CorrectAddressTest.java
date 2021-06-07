package com.example.springproject.algorithm;

import com.example.springproject.SpringProjectApplication;
import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAddress;
import com.example.springproject.algorithm.parser.AddressParser;
import com.example.springproject.algorithm.scoring.AddressScoring;
import com.example.springproject.algorithm.scoring.BasicScoring;
import com.example.springproject.model.Address;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CorrectAddressTest {

    @ParameterizedTest
    @MethodSource
    public void correctAddress(Address address, Address expectedAddress) {
        CorrectAddress correctAddress = new CorrectAddress();
        List<Address> correctedAddress = correctAddress.correctAddress(address);
        assertEquals(expectedAddress.getCity(),correctedAddress.get(0).getCity());
    }

    public static Stream<Arguments> correctAddress(){
        Address address = new Address("Essex","Tendring District", "England","", "");
        Address correctAddress = new Address("England","Essex","Tendring District", "","");
        return Stream.of(Arguments.of(address,correctAddress));
    }


    @ParameterizedTest
    @MethodSource
    public void addressScoring(BasicAddress basicAddress, String expectedCity) {
        AddressScoring basicScoring = new BasicScoring();
        List<ScoredAddress> scoredAdmUnitList = basicScoring.addressScoring(basicAddress);
        assertEquals(scoredAdmUnitList.get(0).getCity().getAdministrativeUnit().getAsciiName().equals(expectedCity), true);
    }

    private static Stream<Arguments> addressScoring(){
        Address address = new Address("Romania","Iasi", "Iassi","", "");
        AddressParser addressParser = new AddressParser();
        BasicAddress basicAddress = addressParser.parseAddress(address);
        String expectedCity = "Municipiul Iasi";
        return Stream.of(Arguments.of(basicAddress, expectedCity));
    }
//
//    @ParameterizedTest
//    @MethodSource
//    public void correctAddressOnlyState(Address address, Address expectedAddress) {
//        CorrectAddress correctAddress = new CorrectAddress();
//        List<Address> correctedAddress = correctAddress.correctAddress(address);
//        assertEquals(expectedAddress.getCity(),correctedAddress.get(0).getCity());
//    }
//
//    public static Stream<Arguments> correctAddressOnlyState(){
//        Address address = new Address("","Iassi", "","", "");
//        Address correctAddress = new Address("România","Iași","Municipiul Iași", "","");
//        return Stream.of(Arguments.of(address,correctAddress));
//    }


    @ParameterizedTest
    @MethodSource
    public void correctAddressEmpty(Address address) {
        CorrectAddress correctAddress = new CorrectAddress();
        List<Address> correctedAddress = correctAddress.correctAddress(address);
        assertEquals(correctedAddress.size(),0);
    }

    public static Stream<Arguments> correctAddressEmpty(){
        Address address = new Address("","", "","", "");
        return Stream.of(Arguments.of(address));
    }

}