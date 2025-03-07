package com.sena.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.proyecto.model.categoriesDTO;

public interface CategoriesRepository extends JpaRepository<categoriesDTO,Integer>{

}
