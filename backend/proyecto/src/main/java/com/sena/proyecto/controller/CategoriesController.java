package com.sena.proyecto.controller;

import com.sena.proyecto.model.Category;
import com.sena.proyecto.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")  // Cambié el path para que sea "/api/categories" y no "/api/products"
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    // Obtener todas las categorías
    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();  // Cambié "getAll()" por "findAll()"
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {
        Optional<Category> category = categoryService.findById(id);  // Cambié "getById()" por "findById()"
        return category.orElse(null);  // Retorna null si no se encuentra la categoría
    }

    // Crear una nueva categoría
    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.save(category);
    }

    // Actualizar una categoría
    @PutMapping("/{id}")
    public Category update(@PathVariable int id, @RequestBody Category category) {
        category.setId(id);
        return categoryService.save(category);
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        categoryService.deleteById(id);
    }
}
