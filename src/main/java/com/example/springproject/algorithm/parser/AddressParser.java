package com.example.springproject.algorithm.parser;

import com.example.springproject.algorithm.model.ScoreAddress;

import java.util.ArrayList;
import java.util.List;

public class AddressParser implements ParserInterface {

    private List<ScoreAddress> addressList = new ArrayList<>();

    @Override
    public List<ScoreAddress> parseAddress(ScoreAddress address) {
        address.setCountry("Parser");
        addressList.add(address);

        return new ArrayList<>(addressList);
    }
}
