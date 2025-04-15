package com.sena.proyecto.service;

import com.sena.proyecto.model.OrderDetail;
import com.sena.proyecto.repository.Orders_DetailsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private Orders_DetailsRepository repository;

    // Obtener todos los registros
    public List<OrderDetail> getAll() {
        return repository.findAll();
    }

    // Obtener uno por ID
    public Optional<OrderDetail> getById(int id) {
        return repository.findById(id);
    }

    // Guardar nuevo registro
    public OrderDetail save(OrderDetail orderDetail) {
        return repository.save(orderDetail);
    }

    // Actualizar un registro existente
    public Optional<OrderDetail> update(int id, OrderDetail updatedDetail) {
        return repository.findById(id).map(existingDetail -> {
            existingDetail.setOrder(updatedDetail.getOrder());
            existingDetail.setProduct(updatedDetail.getProduct());
            existingDetail.setQuantity(updatedDetail.getQuantity());
            return repository.save(existingDetail);
        });
    }

    // Eliminar un registro por ID
    public boolean delete(int id) {
        return repository.findById(id).map(detail -> {
            repository.delete(detail);
            return true;
        }).orElse(false);
    }

    // Filtrar por ID de orden
    public List<OrderDetail> getByOrderId(int orderId) {
        return repository.findByOrderId(orderId);
    }

    // Filtrar por ID de producto
    public List<OrderDetail> getByProductId(int productId) {
        return repository.findByProductId(productId);
    }
}
