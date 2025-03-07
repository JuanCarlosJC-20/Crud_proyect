package com.sena.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.proyecto.model.productDTO;
import com.sena.proyecto.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository ProductRepository;

    public List<productDTO> getAllProduct(){
        return ProductRepository.findAll();
    }

    public productDTO getProducById(int id){
        return ProductRepository.findById(id).get();
    }

    public boolean save(productDTO product){
       ProductRepository.save(product);
        return true;
    }

}
