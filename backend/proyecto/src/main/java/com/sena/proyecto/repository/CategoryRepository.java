package com.sena.proyecto.repository;

import com.sena.proyecto.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Buscar categorías cuyo nombre contenga una cadena (ignora mayúsculas)
    List<Category> findByNameContainingIgnoreCase(String name);
}
