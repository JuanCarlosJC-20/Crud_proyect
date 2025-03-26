package com.sena.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sena.proyecto.model.customersDTO;
import com.sena.proyecto.service.CustomersService;

import responseDTO.responseDTO;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomersController {
 @Autowired
    private CustomersService customersService;

    @PostMapping("/customers")
    public String registerCustomer(
        @RequestBody customersDTO customer
        ){
            customersService.save(customer);
        return "register ok";
    }

     //aplica lo ultimo controller para filtar eliminar etc.
     @GetMapping("/")
    public ResponseEntity<Object> findAllcustomers() {
        List<customersDTO> listcustomers = customersService.getAllCustomer();
        return new ResponseEntity<>(listcustomers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findByIdcustomers(@PathVariable int id) {
        customersDTO customers = customersService.getcustomersById(id);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/search/{filter}")
    public ResponseEntity<Object> search(@PathVariable String filter) {
        List<customersDTO> listcustomers = customersService.getFiltercustomers(filter);
        return new ResponseEntity<>(listcustomers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletecustomers(@PathVariable int id) {
        responseDTO response = customersService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }






}
