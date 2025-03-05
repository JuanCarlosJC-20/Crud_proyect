package com.sena.crud_basic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.repository.ICustomerRepository;
import com.sena.crud_basic.model.customerDTO;

/*
 * Agregamos la anotación bean @Service
 * Para indicar que el archivo es un servicio
 */
@Service
public class CustomerService{

    //se realiza la conexión con el repositorio
    @Autowired
    private ICustomerRepository ICustomerRepository;

    /*
     * crear
     * eliminar
     * actualizar
     * leer lista completa
     * leer el cliente por id
     * adicional los requeridos
     * 
     */





    public List<customerDTO> getAllCustomer(){
        return ICustomerRepository.findAll();
    }

    public customerDTO getCustomerById(int id){
        return ICustomerRepository.findById(id).get();
    }

    public boolean save(customerDTO customer){
        /*
         * if(customer.getId==0)register or create
         * else update
         */
        ICustomerRepository.save(customer);
        return true;
    }
    
}
