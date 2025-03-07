package com.sena.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.proyecto.model.productDTO;

public interface ProductRepository extends JpaRepository<productDTO,Integer> {

}
