package com.example.springproject.controller;

import com.example.springproject.algorithm.CorrectAddress;
import com.example.springproject.logData.PrintLog;
import com.example.springproject.model.Address;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("correct-address")
public class AddressController {

    @PostMapping
    public ResponseEntity<List<Address>> postController(@RequestBody Address address){

        CorrectAddress correctAddress = new CorrectAddress();
        List<Address> correctAddressList = correctAddress.correctAddress(address);
        PrintLog.printLog(address,correctAddressList);
        return new ResponseEntity<>(correctAddressList,HttpStatus.OK);
    }

}
