package com.example.springproject.logData;

import com.example.springproject.model.Address;

import java.util.List;

public class PrintLog {


    public static void printLog(String string){
        LogInterface log = new EventLog();
        log.printLogger(string);
    }

    public static void printLog(Address address, List<Address> correctAddressList) {
        LogInterface log = new EventLog();
        StringBuilder value = new StringBuilder();
        value.append(">>> Received Address: \n");
        value.append(address.toString()).append("\n");
        value.append("------------------------------------\n");
        value.append(">>> Corrected Address: \n");
        for (Address correctAddress: correctAddressList) {
            value.append(correctAddress.toString()).append("\n");
        }
        value.append("\n");
        value.append("======================================\n");
        value.append("\n");
        log.printLogger(value.toString());
    }
}
