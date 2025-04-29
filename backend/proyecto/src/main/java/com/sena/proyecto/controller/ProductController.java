package com.sena.proyecto.controller;

import com.sena.proyecto.model.Product;
import com.sena.proyecto.model.ProductRequest;
import com.sena.proyecto.service.CaptchaService;
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

    @Autowired
    private CaptchaService captchaService;

    // Obtener todos los productos
    @GetMapping("")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo producto (con validación de captcha y validación de datos)
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ProductRequest productRequest) {
        try {
            boolean captchaValid = captchaService.validateCaptcha(productRequest.getCaptchaToken());
            if (!captchaValid) {
                return ResponseEntity.status(403).body("❌ Captcha inválido.");
            }

            Product savedProduct = productService.save(productRequest.getProduct());
            return ResponseEntity.ok(savedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error interno al crear el producto.");
        }
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Product product) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        Product updatedProduct = productService.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Eliminar un producto (con validación de captcha)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable int id,
            @RequestParam("captchaToken") String captchaToken
    ) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        boolean captchaValid = captchaService.validateCaptcha(captchaToken);
        if (!captchaValid) {
            return ResponseEntity.status(403).body("❌ Captcha inválido.");
        }

        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Filtrar productos por nombre
    @GetMapping("/filter/name")
    public ResponseEntity<List<Product>> getByName(@RequestParam String name) {
        List<Product> products = productService.findByNameContaining(name);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // Filtrar productos por categoría
    @GetMapping("/filter/category")
    public ResponseEntity<List<Product>> getByCategory(@RequestParam int categoryId) {
        List<Product> products = productService.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // Filtrar productos por rango de precios
    @GetMapping("/filter/price")
    public ResponseEntity<List<Product>> getByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<Product> products = productService.findByPriceBetween(minPrice, maxPrice);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // Obtener productos por fecha de creación
    @GetMapping("/filter/createdAt")
    public ResponseEntity<List<Product>> getProductsByCreatedAt(@RequestParam LocalDate createdAt) {
        List<Product> products = productService.getProductsByCreatedAt(createdAt);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
}
