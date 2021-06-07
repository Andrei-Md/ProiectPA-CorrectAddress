package com.example.springproject.structures.benchmark;

import com.example.springproject.algorithm.CorrectAddress;
import com.example.springproject.model.Address;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * class that is responsible with correcting a number of addresses
 * from a json
 */
@Slf4j
public class JSONAddressCorrect {

    /**
     * method that gets the number of addresses right corrected from
     * a json file
     *
     * @return the number of addresses right corrected
     */
    public static CorrectAddressBenchmark getNumberOfRightAddresses(String addresses_path, String corrected_addresses_path, boolean debugPrint) {
        CorrectAddressBenchmark correctAddressBenchmark = new CorrectAddressBenchmark();
        List<Address> addresses = readFromJSON(addresses_path);
        List<Address> expectedAddresses = readFromJSON(corrected_addresses_path);
        int count = 0;

        long startTime = 0;
        if (addresses.size() == expectedAddresses.size()) {
            for (int i = 0; i < addresses.size(); i++) {
                if (i == 1) startTime = System.nanoTime();
                Address address = addresses.get(i);
                CorrectAddress correctAddress = new CorrectAddress();
                List<Address> correctedAddresses = correctAddress.correctAddress(address);
                if (correctedAddresses.contains(expectedAddresses.get(i))) {
                    count++;
                } else
                if(debugPrint) {
                    log.info(address.toString());
                }
            }
        }
        long endTime = System.nanoTime();
        correctAddressBenchmark.setNrOfAddresses(addresses.size());
        correctAddressBenchmark.setNrOfCorrectedAddresses(count);
        correctAddressBenchmark.setTimeCorrectAll(endTime - startTime);
        return correctAddressBenchmark;
    }

    /**
     * method that reads from json a number of addresses
     *
     * @param path the path where the json is located
     * @return the list of addresses read from the json file
     */
    private static List<Address> readFromJSON(String path) {
        List<Address> addresses = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(path)) {
            Object obj = jsonParser.parse(reader);
            JSONArray addressesList = (JSONArray) obj;
            addressesList.forEach(emp -> addresses.add(parseAddressObject((JSONObject) emp)));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    /**
     * method that transforms a json object into an address object
     *
     * @param JSONAddress the address in a json object format
     * @return the address
     */
    private static Address parseAddressObject(JSONObject JSONAddress) {
        Address address = new Address();
        address.setCity((String) JSONAddress.get("city"));
        address.setCountry((String) JSONAddress.get("country"));
        address.setState((String) JSONAddress.get("state"));
        address.setPostalCode((String) JSONAddress.get("postalCode"));
        address.setStreetLine((String) JSONAddress.get("streetLine"));
        return address;
    }

    /**
     * method to print the test Report
     *
     * @param reportTitle             report title
     * @param correctAddressBenchmark address benchmark
     */
    public static void printLogs(String reportTitle, CorrectAddressBenchmark correctAddressBenchmark) {
        log.info(reportTitle);
        log.info("\tNumber of addresses: " + correctAddressBenchmark.getNrOfAddresses());
        log.info("\tNumber of addresses corrected: " + correctAddressBenchmark.getNrOfCorrectedAddresses());
        log.info("\tTime to correct: {}", String.format("%.2f ms", correctAddressBenchmark.getCorrectTimeAllMilisec()));
        log.info("\tAverage time to correct an address: {}", String.format("%.2f ms", correctAddressBenchmark.calculateAverageTimeCorrectAddress()));
    }
}
