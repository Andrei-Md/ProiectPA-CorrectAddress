package com.example.springproject.model;

import lombok.Data;

/**
 * class that encapsulates an address
 */
@Data
public class Address {

    private String country;
    private String state;
    private String city;
    private String postalCode;
    private String streetLine;

    public Address(String country, String state, String city, String postalCode, String streetLine) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
        this.streetLine = streetLine;
    }

    public Address() {

    }

    @Override
    public String toString() {
        return "Address: " + '\n' +
                "{\n" +
                "\"country\": \"" + country + "\",\n" +
                "\"state\": \"" + state + "\",\n" +
                "\"city\": \"" + city + "\",\n" +
                "\"postalCode\": \"" + postalCode + "\",\n" +
                "\"streetLine\": \"" + streetLine + "\"\n" +
                '}';
    }
}
