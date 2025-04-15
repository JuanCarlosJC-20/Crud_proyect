package com.sena.proyecto.service;

import com.sena.proyecto.model.Customer;
import com.sena.proyecto.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    // Obtener todos los clientes
    public List<Customer> findAll() {
        return customersRepository.findAll();
    }

    // Buscar cliente por ID
    public Optional<Customer> findById(int id) {
        return customersRepository.findById(id);
    }

    // Guardar o actualizar cliente
    public Customer save(Customer customer) {
        return customersRepository.save(customer);
    }

    // Eliminar cliente por ID
    public void deleteById(int id) {
        customersRepository.deleteById(id);
    }

    // Buscar por nombre (filtro parcial, sin importar mayúsculas/minúsculas)
    public List<Customer> findByName(String name) {
        return customersRepository.findByNameContainingIgnoreCase(name);
    }

    // Buscar por email (filtro parcial, sin importar mayúsculas/minúsculas)
    public List<Customer> findByEmail(String email) {
        return customersRepository.findByEmailContainingIgnoreCase(email);
    }
}
