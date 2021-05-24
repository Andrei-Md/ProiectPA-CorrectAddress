package com.example.springproject.algorithm;

import com.example.springproject.algorithm.model.ScoreAddress;
import com.example.springproject.algorithm.parser.AddressParser;
import com.example.springproject.algorithm.parser.ParserInterface;
import com.example.springproject.algorithm.search.FuzzySearch;
import com.example.springproject.algorithm.search.SearchInterface;
import com.example.springproject.model.Address;

import java.util.ArrayList;
import java.util.List;

public class CorrectAddress {
    private Address address;

    public CorrectAddress(Address address){
        this.address = address;
    }

    public List<Address> correctAddress(){
        ScoreAddress scoreAddress = new ScoreAddress(address);
        List<ScoreAddress> correctAddressList;
        correctAddressList = parseAddress(scoreAddress);
        correctAddressList = searchAddress(correctAddressList);

        return convertListScoreAddress(correctAddressList);
    }

    private  List<ScoreAddress> searchAddress(List<ScoreAddress> scoreAddressList){
        SearchInterface searchInterface = new FuzzySearch();
        return searchInterface.search(scoreAddressList);
    }

    private List<ScoreAddress> parseAddress(ScoreAddress addressList){
        ParserInterface parserInterface = new AddressParser();
        return parserInterface.parseAddress(addressList);
    }

    private List<Address> convertListScoreAddress(List<ScoreAddress> scoreAddressList){
        List<Address> correctAddressList = new ArrayList<>();
        for (ScoreAddress scoreAddress: scoreAddressList) {
            correctAddressList.add(scoreAddress.convertScoreAddress());
        }
        return  correctAddressList;
    }

}
