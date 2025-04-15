package com.sena.proyecto.controller;

import com.sena.proyecto.model.Order;
import com.sena.proyecto.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    // Obtener todas las órdenes
    @GetMapping
    public List<Order> getAll() {
        return ordersService.findAll();
    }

    // Obtener una orden por ID
    @GetMapping("/{id}")
    public Order getById(@PathVariable int id) {
        Optional<Order> order = ordersService.findById(id);
        return order.orElse(null); // Si no se encuentra, retorna null
    }

    // Crear una nueva orden
    @PostMapping
    public Order create(@RequestBody Order order) {
        return ordersService.save(order);
    }

    // Actualizar una orden existente
    @PutMapping("/{id}")
    public Order update(@PathVariable int id, @RequestBody Order order) {
        order.setId(id); // Se asegura que el ID se establezca antes de guardar
        return ordersService.save(order);
    }

    // Eliminar una orden por ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        ordersService.deleteById(id);
    }

    

  
    // Filtrar órdenes por ID de cliente
    @GetMapping("/filter-by-customer")
    public List<Order> getByCustomerId(@RequestParam int customerId) {
        return ordersService.findByCustomerId(customerId);
    }
}
