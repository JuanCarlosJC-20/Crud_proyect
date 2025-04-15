package com.sena.proyecto.controller;

import com.sena.proyecto.model.OrderDetail;
import com.sena.proyecto.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/order-details") // Ruta base para las peticiones HTTP
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (útil para frontend)
public class OrderDetailController {

    @Autowired // Inyecta el servicio de OrderDetail
    private OrderDetailService orderDetailService;

    // Obtener todos los detalles de órdenes
    @GetMapping
    public List<OrderDetail> getAll() {
        return orderDetailService.getAll();
    }

    // Obtener un detalle de orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getById(@PathVariable int id) {
        return orderDetailService.getById(id)
                .map(ResponseEntity::ok) // Si existe, retorna 200 OK con el objeto
                .orElse(ResponseEntity.notFound().build()); // Si no existe, retorna 404
    }

    // Crear un nuevo detalle de orden
    @PostMapping
    public OrderDetail create(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.save(orderDetail);
    }

    // Actualizar un detalle de orden existente
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> update(@PathVariable int id, @RequestBody OrderDetail orderDetail) {
        return orderDetailService.update(id, orderDetail)
                .map(ResponseEntity::ok) // Retorna 200 con el objeto actualizado
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 si no se encontró
    }

    // Eliminar un detalle de orden por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return orderDetailService.delete(id)
                ? ResponseEntity.ok().build() // Retorna 200 si se eliminó correctamente
                : ResponseEntity.notFound().build(); // Retorna 404 si no se encontró
    }

    // Filtrar detalles por ID de orden
    @GetMapping("/order/{orderId}")
    public List<OrderDetail> getByOrderId(@PathVariable int orderId) {
        return orderDetailService.getByOrderId(orderId);
    }

    // Filtrar detalles por ID de producto
    @GetMapping("/product/{productId}")
    public List<OrderDetail> getByProductId(@PathVariable int productId) {
        return orderDetailService.getByProductId(productId);
    }
}
