package com.example.springproject.benchmark;

import com.example.springproject.structures.GlobalUtil;
import com.example.springproject.structures.benchmark.CorrectAddressBenchmark;
import com.example.springproject.structures.benchmark.JSONAddressCorrect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class AddressCorrectingBenchmarks {

    @ParameterizedTest
    @MethodSource
    public void correctAddresses(String addressesPath, String expectedAddressesPath){
        CorrectAddressBenchmark correctAddressBenchmark = JSONAddressCorrect.getNumberOfRightAddresses(addressesPath,expectedAddressesPath,false);
        Assertions.assertTrue(correctAddressBenchmark.getNrOfCorrectedAddresses() != 0);
    }

    public static Stream<Arguments> correctAddresses(){
        return Stream.of(Arguments.of(GlobalUtil.ADDRESSES_PATH,GlobalUtil.CORRECTED_ADDRESSES_PATH));
    }

    @Test
    public void computeTime(){
        CorrectAddressBenchmark correctAddressBenchmark = new CorrectAddressBenchmark();
        correctAddressBenchmark.setTimeCorrectAll(System.nanoTime());
        correctAddressBenchmark.setNrOfAddresses(20);
        Assertions.assertTrue(correctAddressBenchmark.calculateAverageTimeCorrectAddress() != 0 && correctAddressBenchmark.getTimeCorrectAll() != 0);
    }

}
