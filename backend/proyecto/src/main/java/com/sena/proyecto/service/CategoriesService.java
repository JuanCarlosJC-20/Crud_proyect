package com.sena.proyecto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.proyecto.model.categoriesDTO;
import com.sena.proyecto.repository.CategoriesRepository;



@Service
public class CategoriesService {
    @Autowired
    private CategoriesRepository CategoriesRepository;
    
     public List<categoriesDTO> getAllCustomer(){
        return CategoriesRepository.findAll();
    }

    public categoriesDTO getCategoriesById(int id){
        return CategoriesRepository.findById(id).get();
    }

    public boolean save(categoriesDTO categories){
       
        CategoriesRepository.save(categories);
        return true;
    }
}
