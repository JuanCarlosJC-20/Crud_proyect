package com.sena.proyecto.controller;

import com.sena.proyecto.model.Product;
import com.sena.proyecto.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Obtener todos los productos
    @GetMapping("")
    public List<Product> getAll() {
        return productService.findAll();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo producto
    @PostMapping("")
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }

    // Actualizar un producto
    @PutMapping("/{id}")
    public Product update(@PathVariable int id, @RequestBody Product product) {
        product.setId(id);
        return productService.save(product);
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        productService.deleteById(id);
    }

    // Filtrar productos por nombre
    @GetMapping("/filter/name")
    public List<Product> getByName(@RequestParam String name) {
        return productService.findByNameContaining(name);
    }

    // Filtrar productos por categoría
    @GetMapping("/filter/category")
    public List<Product> getByCategory(@RequestParam int categoryId) {
        return productService.findByCategoryId(categoryId);
    }

    // Filtrar productos por rango de precios
    @GetMapping("/filter/price")
    public List<Product> getByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return productService.findByPriceBetween(minPrice, maxPrice);
    }

     // Endpoint para obtener productos por la fecha de creación
    @GetMapping("/createdAt")
    public ResponseEntity<List<Product>> getProductsByCreatedAt(@RequestParam LocalDate createdAt) {
        List<Product> products = productService.getProductsByCreatedAt(createdAt);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
}
