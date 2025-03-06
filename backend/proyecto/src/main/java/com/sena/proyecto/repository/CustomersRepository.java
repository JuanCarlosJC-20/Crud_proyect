package com.sena.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.proyecto.model.customersDTO;

public interface CustomersRepository extends JpaRepository<customersDTO,Integer> {

}
