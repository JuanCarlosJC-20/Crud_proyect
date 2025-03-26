package com.sena.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.proyecto.model.productDTO;
import com.sena.proyecto.repository.ProductRepository;

import responseDTO.responseDTO;

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

  

    //aqui empiza el codigo de flitarar y todo
    public List<productDTO> getFilterproducto(String filter) {
        return ProductRepository.search(filter);
    }

    public productDTO getproductById(int id) {
        return ProductRepository.findById(id).get();
    }

    
    public responseDTO save(productDTO product) {
        if (product.getName().length() < 1 || product.getName().length() > 255) {
            responseDTO response = new responseDTO(
                    "Error",
                    "El nombre debe tener una longitud entre 1 y 255 caracteres");
            return response;
        }

    ProductRepository.save(product);
    responseDTO response = new responseDTO(
        "ok",
        "Se registro correctamente");

         return response;
        // return true;
    }
    // for delet
    public responseDTO delete(int id) {
        
        productDTO product = getproductById(id);
        product.setStatus(1);
        ProductRepository.save(product);
        responseDTO response = new responseDTO(
                "OK",
                "Se eliminó correctamente");
        return response;
    }

}
