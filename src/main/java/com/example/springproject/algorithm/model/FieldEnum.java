package com.example.springproject.algorithm.model;

/**
 * enumeration for the fields
 */
public enum FieldEnum {
    Country(0),
    State(1),
    City(2),
    PostalCode(3),
    StreetLine(4),
    Unknown(5);

    private final int id;

    FieldEnum(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

}
