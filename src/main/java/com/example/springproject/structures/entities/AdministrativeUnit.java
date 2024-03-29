package com.example.springproject.structures.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Setter
@Getter
public class AdministrativeUnit implements Serializable {
    private String id;
    private String name;
    private String asciiName;
    private String parsedName;
    private List<String> alternateNames = new ArrayList<>();
    private Double latitude;
    private Double longitude;
    private String countryCode;
    private AdministrativeUnit superDivision;
    private int level;
    private Map<String, AdministrativeUnit> subDivision = new TreeMap<>();


    /**
     * Constructor used to initialize an administrative unit from String list
     *
     * @param row the csv row
     * @return an administrative unit
     */
    public AdministrativeUnit(String[] row, String alternateNamesDelimiter) {
        this.id = row[0];
        this.name = row[1];
        this.asciiName = row[2];
        this.alternateNames = parseList(row[3], alternateNamesDelimiter);
        this.latitude = Double.parseDouble(row[4]);
        this.longitude = Double.parseDouble(row[5]);
        this.countryCode = row[6];
        this.parsedName = "";
    }

    /**
     * copy Constructor
     * @param administrativeUnit
     */
    public AdministrativeUnit(AdministrativeUnit administrativeUnit){
        this.id = administrativeUnit.getId();
        this.name = administrativeUnit.getName();
        this.asciiName = administrativeUnit.getAsciiName();
        this.parsedName = administrativeUnit.getParsedName();
        this.latitude = administrativeUnit.getLatitude();
        this.longitude = administrativeUnit.getLongitude();
        this.countryCode = administrativeUnit.getCountryCode();
        this.level = administrativeUnit.getLevel();
        this.superDivision = administrativeUnit.getSuperDivision();
        this.subDivision = administrativeUnit.getSubDivision();
    }


    /**
     * method used to parse a string with a specified delimiter
     *
     * @param string the string
     * @return a list of string
     */
    private List<String> parseList(String string, String alternateNamesDelimiter) {
        if (string.isEmpty())
            return new ArrayList<>();
        String delimiter = alternateNamesDelimiter;
        String[] splitString = string.split(delimiter);
        return new ArrayList<>(Arrays.asList(splitString));
    }

    public AdministrativeUnit getSuperDivision(){
        return this.superDivision;
    }

    public Map<String, AdministrativeUnit> getSubDivision(){
        return this.subDivision;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdministrativeUnit that = (AdministrativeUnit) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(asciiName, that.asciiName) && Objects.equals(alternateNames, that.alternateNames) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(countryCode, that.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, asciiName, alternateNames, latitude, longitude, countryCode);
    }

    @Override
    public String toString() {
        return "AdmUnit{" +
                "asciiName='" + asciiName + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parsedName='" + parsedName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", countryCode='" + countryCode + '\'' +
                ", alternateNames=" + alternateNames +
                '}';
    }
}
