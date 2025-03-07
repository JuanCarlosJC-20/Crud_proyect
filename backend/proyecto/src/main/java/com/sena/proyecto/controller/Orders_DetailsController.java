package com.sena.proyecto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sena.proyecto.model.orders_detailsDTO;
import com.sena.proyecto.service.Orders_DatailsService;
@RestController
public class Orders_DetailsController {
    @Autowired Orders_DatailsService  orders_DatailsService;

        @PostMapping("/orders_details")
    public String registerOrders_Details(
        @RequestBody orders_detailsDTO orders_details
        ){
            orders_DatailsService.save(orders_details);
        return "register ok";
    }
}
