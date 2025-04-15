package com.sena.proyecto.service;

import com.sena.proyecto.model.Category;
import com.sena.proyecto.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Obtener todas las categorías
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // Obtener una categoría por su ID
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    // Crear o actualizar una categoría
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    // Eliminar una categoría por ID
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    // Buscar categorías por nombre (filtrado parcial, sin importar mayúsculas/minúsculas)
    public List<Category> findByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
}
