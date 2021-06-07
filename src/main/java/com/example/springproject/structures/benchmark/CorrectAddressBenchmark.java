package com.example.springproject.structures.benchmark;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorrectAddressBenchmark {
    private int nrOfAddresses=0;
    private int nrOfCorrectedAddresses=0;
    private long timeCorrectAll=0;

    /**
     * method to calculate the average time based on total number of addressed parsed
     * @return the average time to correct an address
     */
    public double calculateAverageTimeCorrectAddress() {
        return ((double) timeCorrectAll / nrOfAddresses) / Math.pow(10, 6);
    }

    /**
     * method to convert the total time in milliseconds
     * @return the total time in milliseconds
     */
    public double getCorrectTimeAllMilisec() {
        return timeCorrectAll / Math.pow(10, 6);
    }

}
