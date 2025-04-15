package com.sena.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sena.proyecto.model.OrderDetail;

public interface Orders_DetailsRepository extends JpaRepository<OrderDetail,Integer>{
 // Método para obtener los detalles de la orden filtrados por ID de la orden
 List<OrderDetail> findByOrderId(int orderId);

 // Método para obtener los detalles de la orden filtrados por ID de producto
 List<OrderDetail> findByProductId(int productId);
}
