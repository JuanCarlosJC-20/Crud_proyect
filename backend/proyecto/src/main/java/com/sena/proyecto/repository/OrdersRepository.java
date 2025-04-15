package com.sena.proyecto.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.proyecto.model.Order;

public interface OrdersRepository extends JpaRepository<Order,Integer>{



// Buscar por ID del cliente
List<Order> findByCustomerId(int customerId);
    
}
