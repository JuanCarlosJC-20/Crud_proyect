package com.sena.proyecto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.proyecto.model.categoriesDTO;
import com.sena.proyecto.repository.CategoriesRepository;

import responseDTO.responseDTO;




@Service
public class CategoriesService {
    @Autowired
    public CategoriesRepository CategoriesRepository;
    
     public List<categoriesDTO> getAllCategories(){
        return CategoriesRepository.findAll();
    }

    public categoriesDTO getCategoriesById(int id){
        return CategoriesRepository.findById(id).get();
    }
// aqui empiza el codigo de guardar eliminar filtrar
    public List<categoriesDTO> getFiltercategories(String filter) {
        return CategoriesRepository.search(filter);
    }

    public categoriesDTO getcategoriesById(int id) {
        return CategoriesRepository.findById(id).get();
    }

    //guardar 

    public responseDTO save(categoriesDTO categories) {
        if (categories.getName().length() < 1 || categories.getName().length() > 255) {
            responseDTO response = new responseDTO(
                    "Error",
                    "El titulo debe tener una longitud entre 1 y 255 caracteres");
            return response;
        }

    CategoriesRepository.save(categories);
    responseDTO response = new responseDTO(
        "ok",
        "Se registro correctamente");

         return response;
        // return true;
    }
    // for delet
    public responseDTO delete(int id) {
        
        categoriesDTO categories = getCategoriesById(id);
        categories.setStatus(0);
        CategoriesRepository.save(categories);
        responseDTO response = new responseDTO(
                "OK",
                "Se eliminó correctamente");
        return response;
    }

}
