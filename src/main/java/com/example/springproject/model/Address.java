package com.example.springproject.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Address {

    private String country;
    private String state;
    private String city;
    private String postalCode;
    private String streetLine;

    public Address(String country, String state, String city, String postalCode, String streetLine){
        this.country = country;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
        this.streetLine = streetLine;
    }
}
