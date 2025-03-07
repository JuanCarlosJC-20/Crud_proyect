package com.sena.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.proyecto.model.ordersDTO;
import com.sena.proyecto.repository.OrdersRepository;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository OrdersRepository;
    
      public List<ordersDTO> getAllOrders(){
        return OrdersRepository.findAll();
    }

    public ordersDTO getOrdersById(int id){
        return OrdersRepository.findById(id).get();
    }

     public boolean save(ordersDTO orders){
       
        OrdersRepository.save(orders);
        return true;
    }
   
}
