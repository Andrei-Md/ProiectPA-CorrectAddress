package com.example.springproject.structures;

import java.util.ArrayList;
import java.util.List;

public class GlobalUtil {

    public final static String ADMINISTRATIVE_UNIT_SERIALIZE_PATH = "resources/saves/administrative-unit.ser";
    public final static String FILE_PATH_HIERARCHY = "resources/in/hierarchy.csv";
    public final static String FILE_PATH_CITIES = "resources/in/cities.csv";
    public final static char HIERARCHY_DELIM = ',';
    public final static char CITIES_DELIM = '|';
    public final static String ALTERNATE_NAMES_DELIM = ",";
    public final static String[] ADMINISTRATIVE_NAME_PREFIX = {"comuna", "municipiul", "oras", "jud.", "borough of", "city and borough of", "city of", "isle of", "isles of","metropolitan borough"};
    public final static String[] ADMINISTRATIVE_NAME_SUFFIX = {"district"};
    public final static String ADMINISTRATIVE_UNIT_DELIMITERS = " |-";
    public final static String[] ADMINISTRATIVE_UNIT_DELIMITER_LIST = {" ", "-"};
    public final static int ADMINISTRATIVE_UNIT_NAME_MIN_CHARS_NO = 4;
    public final static List<String> ROOT_LIST = new ArrayList() {{
        add("6269131"); //England
        add("798549"); //Romania
        add("2641364"); //Northen Ireland
        add("2634895"); //Wales
        add("2638360"); //Scotland
    }};

    public static final String ADDRESSES_PATH = "resources/in/tests/addresses.txt";
    public static final String CORRECTED_ADDRESSES_PATH = "resources/in/tests/correct-addresses.txt";
    public static final String ADDRESSES_FUZZY_PATH = "resources/in/tests/address-fuzzy.txt";
    public static final String CORRECTED_ADDRESSES_FUZZY_PATH = "resources/in/tests/correct-addresses-fuzzy.txt";

}
