package com.sena.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sena.proyecto.service.OrdersService;
import com.sena.proyecto.model.ordersDTO;

@RestController
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

     @PostMapping("/orders")
    public String registerOrders(
        @RequestBody ordersDTO orders
        ){
            ordersService.save(orders);
        return "register ok";
    }

}
