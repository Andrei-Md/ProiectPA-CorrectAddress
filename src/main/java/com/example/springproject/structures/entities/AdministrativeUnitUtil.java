package com.example.springproject.structures.entities;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.*;

public class AdministrativeUnitUtil {

    public final static String ADMINISTRATIVE_UNIT_SERIALIZE_PATH = "resources/saves/administrative-unit.ser";
    private final static String FILE_PATH_HIERARCHY = "resources/in/hierarchy.csv";
    private final static String FILE_PATH_CITIES = "resources/in/cities.csv";
    private final static char HIERARCHY_DELIM = ',';
    private final static char CITIES_DELIM = '|';
    private final static String ALTERNATE_NAMES_DELIM = ",";

    private final static List<String> rootList = new ArrayList() {{
        add("798549"); //Romania
    }};

    /**
     * method used to load the serialized entity
     *
     * @param path the path from where to load the entity
     * @return the loaded entity
     */
    public static AdministrativeHierarchy loadAdministrativeHierarchy(String path) {
        AdministrativeHierarchy newAdministrativeHierarchy;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            {
                newAdministrativeHierarchy = (AdministrativeHierarchy) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException exception) {
//            exception.printStackTrace();
            return new AdministrativeHierarchy(path);
        }
        newAdministrativeHierarchy.setPath(path);

        return new AdministrativeHierarchy(newAdministrativeHierarchy);
    }

    /**
     * method used to save the serialized entity
     *
     * @param administrativeHierarchy the entity to save
     */
    public static void saveAdministrativeHierarchy(AdministrativeHierarchy administrativeHierarchy) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(administrativeHierarchy.getPath()))) {
            oos.writeObject(administrativeHierarchy);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * method used to read from a csv file
     *
     * @return the list of parsed csv
     */
    private static List<String[]> readFromCsv(String filePath, char delimitator) { //Map<String, AdministrativeUnit>
        List<String[]> result = null;
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(delimitator).build();
            CSVReader reader = new CSVReaderBuilder(
                    new FileReader(filePath))
                    .withCSVParser(csvParser)
                    .withSkipLines(1)
                    .build();
            result = reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * method to serialize the administrative unit hierarchy
     *
     * @param filePath the file path where to save the Administrative Hierarchy
     */
    public static void serializeAdministrativeHierarchy(String filePath) {
        AdministrativeHierarchy administrativeHierarchy = new AdministrativeHierarchy(filePath);

        SetMultimap<String, String> administrativeUnitHierarchy = createAdministrativeUnitHierarchy();
        administrativeHierarchy.setAdministrativeUnitHierarchy(administrativeUnitHierarchy);

        Map<String, AdministrativeUnit> administrativeUnitMap = createAdministrativeUnitMap(administrativeHierarchy);
        administrativeHierarchy.setAdministrativeUnitMap(administrativeUnitMap);

        administrativeHierarchy.setPath(filePath);
        saveAdministrativeHierarchy(administrativeHierarchy);
    }

    private static Map<String, AdministrativeUnit> createAdministrativeUnitMap(AdministrativeHierarchy administrativeHierarchy) {
        List<String[]> csvRows = readFromCsv(FILE_PATH_CITIES, CITIES_DELIM);
        Map<String, AdministrativeUnit> administrativeUnitMapAll = new HashMap<>();
        for (String[] row : csvRows) {
            AdministrativeUnit administrativeUnit = new AdministrativeUnit(row, ALTERNATE_NAMES_DELIM);
            administrativeUnitMapAll.put(administrativeUnit.getId(), administrativeUnit);
        }

        Map<String, AdministrativeUnit> administrativeUnitMap = new TreeMap<>();

        //loop through all roots
        for (String root : rootList) {
            AdministrativeUnit rootAdministrativeUnit = administrativeUnitMapAll.get(root);
            HashSet<String> rootSet = new HashSet<>();
            rootSet.addAll(administrativeHierarchy.getAdministrativeUnitHierarchy().get(root));
            rootAdministrativeUnit.setSuperDivision(null);  //set superdivision
            rootAdministrativeUnit.setLevel(0);             //set level

            createHierarchy(administrativeHierarchy, administrativeUnitMapAll, rootAdministrativeUnit, rootSet, 1);
            administrativeUnitMap.put(rootAdministrativeUnit.getAsciiName(), rootAdministrativeUnit);
        }

        return administrativeUnitMap;
    }

    /**
     * method used to create a hierarchy starting from a root
     *
     * @param superdivisionAdmUnit current administrative unit
     * @param subdivisonSet        subdivision set for that administrative unit
     * @param level                division level
     */
    private static void createHierarchy(AdministrativeHierarchy administrativeHierarchy, Map<String, AdministrativeUnit> administrativeUnitMapAll, AdministrativeUnit superdivisionAdmUnit, HashSet<String> subdivisonSet, int level) {
        if (subdivisonSet.isEmpty())
            return;
        for (String code : subdivisonSet) {
            //add to map the current child
            AdministrativeUnit currentAdmUnit = administrativeUnitMapAll.get(code);
            superdivisionAdmUnit.getSubDivision().put(currentAdmUnit.getAsciiName(), currentAdmUnit);
            currentAdmUnit.setSuperDivision(superdivisionAdmUnit);  //set superdivision
            currentAdmUnit.setLevel(level);                         //set level

            //get current administrative unit subdivisions
            HashSet<String> newSubdivisonSet = new HashSet<>(administrativeHierarchy.getAdministrativeUnitHierarchy().get(code));
            createHierarchy(administrativeHierarchy, administrativeUnitMapAll, currentAdmUnit, newSubdivisonSet, level + 1);
        }
    }

    /**
     * method to create the Administrative Unit Hierarchy from csv
     */
    private static SetMultimap<String, String> createAdministrativeUnitHierarchy() {
        List<String[]> csvRows = readFromCsv(FILE_PATH_HIERARCHY, HIERARCHY_DELIM);
        SetMultimap<String, String> administrativeUnitHierarchy = HashMultimap.create();
        for (String[] row : csvRows) {
            administrativeUnitHierarchy.put(row[0], row[1]);
        }
        return administrativeUnitHierarchy;
    }


}
