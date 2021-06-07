package com.example.springproject.structures.benchmark;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorrectAddressBenchmark {
    private int nrOfAddresses=0;
    private int nrOfCorrectedAddresses=0;
    private long timeCorrectAll=0;


    public long calculateAverageTimeCorrectAddress(){
        return (long) timeCorrectAll/nrOfAddresses;
    }

}
