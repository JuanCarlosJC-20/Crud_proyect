package com.sena.proyecto.service;

import com.sena.proyecto.model.Order;
import com.sena.proyecto.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    // Obtener todas las órdenes
    public List<Order> findAll() {
        return ordersRepository.findAll();
    }

    // Buscar una orden por ID
    public Optional<Order> findById(int id) {
        return ordersRepository.findById(id);
    }

    // Guardar o actualizar una orden
    public Order save(Order order) {
        return ordersRepository.save(order);
    }

    // Eliminar una orden por ID
    public void deleteById(int id) {
        ordersRepository.deleteById(id);
    }

   

 

    // Filtrar órdenes por ID del cliente
    public List<Order> findByCustomerId(int customerId) {
        return ordersRepository.findByCustomerId(customerId);
    }
}
