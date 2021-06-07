package com.example.springproject.logData;

import com.example.springproject.model.Address;

import java.util.Iterator;
import java.util.List;

/**
 * utility class for event logs
 */
public class PrintLog {

    /**
     * utility method used to print in event log file
     *
     * @param string
     */
    public static void printLog(String string) {
        LogInterface log = new EventLog();
        log.printLogger(string);
    }

    /**
     * utility method used to print in event log file
     *
     * @param address
     * @param correctAddressList
     */
    public static void printLog(Address address, List<Address> correctAddressList) {
        LogInterface log = new EventLog();
        StringBuilder value = new StringBuilder();
        value.append("\n>>> Received Address: \n");
        value.append(address.toString()).append("\n");
        value.append("------------------------------------\n");
        value.append("\n>>> Corrected Address: \n");
        Iterator<Address> itCorrectAddress = correctAddressList.listIterator();
        while (itCorrectAddress.hasNext()) {
            Address correctAddress = itCorrectAddress.next();
            value.append(correctAddress.toString()).append("\n");
            if (itCorrectAddress.hasNext())
                value.append("---\n");
        }
        value.append("\n");
        value.append("======================================\n");
        log.printLogger(value.toString());
    }
}
