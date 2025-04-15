package com.sena.proyecto.controller;

import com.sena.proyecto.model.Customer;
import com.sena.proyecto.service.CustomersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")  // Ruta del controlador de clientes
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    // Obtener todos los clientes
    @GetMapping
    public List<Customer> getAll() {
        return customersService.findAll();  // Cambié "getAll()" por "findAll()"
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public Customer getById(@PathVariable int id) {
        Optional<Customer> customer = customersService.findById(id);  // Cambié "getById()" por "findById()"
        return customer.orElse(null);  // Retorna null si no se encuentra el cliente
    }

    // Crear un nuevo cliente
    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return customersService.save(customer);
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
    public Customer update(@PathVariable int id, @RequestBody Customer customer) {
        customer.setId(id);
        return customersService.save(customer);
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        customersService.deleteById(id);  // Cambié "delete()" por "deleteById()" para que coincida con el servicio
    }
}
