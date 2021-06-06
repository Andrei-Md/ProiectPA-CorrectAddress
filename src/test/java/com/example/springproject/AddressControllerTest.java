package com.example.springproject;

import com.example.springproject.controller.AddressController;
import com.example.springproject.model.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AddressControllerTest {

    @Autowired
    private AddressController addressController;

    @Test
    public void controllerTest1(){
        Address address = new Address("Romania","Iasi", "Iassi","", "");
        ResponseEntity<List<Address>> addreses = addressController.postController(address);
        assertNotNull(addreses.getBody());
    }

    @Test
    public void controllerTest2(){
        Address address = new Address("Romania","Piatra", "Neamt","", "");
        ResponseEntity<List<Address>> addreses = addressController.postController(address);
        assertNotNull(addreses.getBody());
    }

}
