package com.sena.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.proyecto.model.ordersDTO;

public interface OrdersRepository extends JpaRepository<ordersDTO,Integer>{

 
}
