package com.example.springproject.algorithm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoredAddressTest {

    @Test
    void computeTotal() {
        ScoredAddress scoredAddress = new ScoredAddress();
        scoredAddress.setCountryScore(10);
        scoredAddress.setCityScore(5);
        scoredAddress.setStateScore(20);
        scoredAddress.computeTotal();
        assertEquals(scoredAddress.getTotal(),35);
    }
}