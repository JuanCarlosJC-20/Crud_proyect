package com.sena.proyecto.service;

import com.sena.proyecto.model.Product;
import com.sena.proyecto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CaptchaService captchaService; // Inyectar el CaptchaService

    // Obtener todos los productos
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Buscar un producto por ID
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    // Guardar o actualizar un producto
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Eliminar un producto por ID
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    // Filtrar productos por nombre
    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    // Filtrar productos por categoría
    public List<Product> findByCategoryId(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Método para obtener productos por createdAt
    public List<Product> getProductsByCreatedAt(LocalDate createdAt) {
        return productRepository.findByCreatedAt(createdAt);
    }

    // Filtrar productos por rango de precios
    public List<Product> findByPriceBetween(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // Método para guardar un producto con validación del captcha
    public Product saveWithCaptchaValidation(Product product, String captchaToken) {
        // Validar el captcha con el CaptchaService
        if (!captchaService.validateCaptcha(captchaToken)) {
            throw new IllegalArgumentException("Captcha inválido");
        }

        // Si el captcha es válido, proceder a guardar el producto
        return productRepository.save(product);
    }

   

    // Implementación de existsById
public boolean existsById(int id) {
    return productRepository.existsById(id);
}

}
