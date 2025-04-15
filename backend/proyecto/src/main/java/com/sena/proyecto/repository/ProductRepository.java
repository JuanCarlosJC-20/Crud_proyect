package com.sena.proyecto.repository;



import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



import com.sena.proyecto.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
 //filtrar por fecha de creacion

    List<Product> findByCreatedAt(LocalDate createdAt);

    // Filtrar productos por nombre
    List<Product> findByNameContaining(String name);

    // Filtrar productos por categoría (suponiendo que la relación con Category está configurada)
    List<Product> findByCategoryId(int categoryId);

    // Filtrar productos por rango de precios
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
}
