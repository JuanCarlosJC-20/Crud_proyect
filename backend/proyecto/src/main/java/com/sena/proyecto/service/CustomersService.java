package com.sena.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.proyecto.repository.CustomersRepository;
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

    public boolean save(customersDTO customer){
        /*
         * if(customer.getId==0)register or create
         * else update
         */
        CustomersRepository.save(customer);
        return true;
    }
}
