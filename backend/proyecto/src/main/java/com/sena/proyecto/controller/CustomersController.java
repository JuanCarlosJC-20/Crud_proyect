package com.sena.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sena.proyecto.model.customersDTO;
import com.sena.proyecto.service.CustomersService;


@RestController
public class CustomersController {
 @Autowired
    private CustomersService customersService;

    //método para crear un registro POST
    @PostMapping("/customers")
    public String registerCustomer(
        @RequestBody customersDTO customer
        ){
            customersService.save(customer);
        return "register ok";
    }






    @GetMapping("/")
    public ResponseEntity<Object> getCustomerAll() {
        var prueba=customersService.getAllCustomer();
        
        return new ResponseEntity<>(prueba, HttpStatus.OK);
    }

    

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerId(@PathVariable int id) {
        var prueba=customersService.getCustomerById(id);
        
        return new ResponseEntity<>(prueba, HttpStatus.OK);
    }

}
