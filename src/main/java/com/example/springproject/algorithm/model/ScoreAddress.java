package com.example.springproject.algorithm.model;

import com.example.springproject.model.Address;
import lombok.Data;

@Data
public class ScoreAddress {
    private String country;
    private String state;
    private String city;
    private String postalCode;
    private String streetLine;

    private int countryScore;
    private int stateScore;
    private int cityScore;
    private int postalCodeScore;
    private int streetLineScore;
    private int total;

    public ScoreAddress(Address address){
        this.country = address.getCountry();
        this.state = address.getState();
        this.city = address.getCity();
        this.postalCode = address.getPostalCode();
        this.streetLine = address.getStreetLine();

        this.countryScore = 0;
        this.stateScore = 0;
        this.cityScore = 0;
        this.postalCodeScore = 0;
        this.streetLineScore = 0;
    }

    public Address convertScoreAddress(){
        return new Address(this.getCountry(),this.getState(), this.getCity(), this.getPostalCode(), this.getStreetLine());
    }


}
