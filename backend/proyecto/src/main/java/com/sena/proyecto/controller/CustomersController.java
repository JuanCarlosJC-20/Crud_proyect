package com.sena.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sena.proyecto.model.customersDTO;
import com.sena.proyecto.service.CustomersService;


@RestController
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






}
