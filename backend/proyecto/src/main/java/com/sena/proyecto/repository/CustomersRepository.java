package com.sena.proyecto.repository;

import com.sena.proyecto.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer> {

    // Buscar clientes cuyo nombre contenga una cadena (ignora mayúsculas)
    List<Customer> findByNameContainingIgnoreCase(String name);

    // Buscar clientes por email (filtro exacto, pero puedes personalizarlo)
    List<Customer> findByEmailContainingIgnoreCase(String email);
}
