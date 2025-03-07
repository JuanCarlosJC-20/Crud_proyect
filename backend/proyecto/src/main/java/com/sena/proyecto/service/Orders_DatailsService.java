package com.sena.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.proyecto.model.orders_detailsDTO;
import com.sena.proyecto.repository.Orders_DetailsRepository;

@Service
public class Orders_DatailsService {

  @Autowired
    private  Orders_DetailsRepository Orders_DetailsRepository;

    public List<orders_detailsDTO> getAllOrders_details(){
        return Orders_DetailsRepository.findAll();
    }

    public orders_detailsDTO getOrders_detailsById(int id){
        return Orders_DetailsRepository.findById(id).get();
    }

     public boolean save(orders_detailsDTO orders_details){
        Orders_DetailsRepository.save(orders_details);
        return true;
    }

}
