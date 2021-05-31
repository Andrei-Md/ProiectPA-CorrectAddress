package com.example.springproject.algorithm.parser;


import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.model.Address;

import java.util.List;

public interface ParserInterface {

    public BasicAddress parseAddress(Address address);

}
