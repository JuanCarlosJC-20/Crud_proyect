package com.sena.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.proyecto.repository.CustomersRepository;

import responseDTO.responseDTO;

import com.sena.proyecto.model.customersDTO;
import java.util.List;

@Service
public class CustomersService {
 //se realiza la conexión con el repositorio
    @Autowired
    private CustomersRepository CustomersRepository;
    /*
     * crear
     * eliminar
     * actualizar
     * leer lista completa
     * leer el cliente por id
     * adicional los requeridos
     * 
     */
    public List<customersDTO> getAllCustomer(){
        return CustomersRepository.findAll();
    }

    public customersDTO getCustomerById(int id){
        return CustomersRepository.findById(id).get();
    }

   
            //aqui empiza el codigo de flitarar y todo
    public List<customersDTO> getFiltercustomers(String filter) {
        return CustomersRepository.search(filter);
    }

    public customersDTO getcustomersById(int id) {
        return CustomersRepository.findById(id).get();
    }

    
    public responseDTO save(customersDTO customers) {
        if (customers.getName().length() < 1 || customers.getName().length() > 255) {
            responseDTO response = new responseDTO(
                    "Error",
                    "El nombre debe tener una longitud entre 1 y 255 caracteres");
            return response;
        }

    CustomersRepository.save(customers);
    responseDTO response = new responseDTO(
        "ok",
        "Se registro correctamente");

         return response;
        // return true;
    }
    // for delet
    public responseDTO delete(int id) {
        
        customersDTO customers = getcustomersById(id);
        customers.setStatus(1);
        CustomersRepository.save(customers);
        responseDTO response = new responseDTO(
                "OK",
                "Se eliminó correctamente");
        return response;
    }
   

}
