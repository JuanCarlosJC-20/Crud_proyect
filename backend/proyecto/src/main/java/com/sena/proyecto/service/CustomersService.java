package com.sena.proyecto.service;

import com.sena.proyecto.model.Customer;
import com.sena.proyecto.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private CaptchaService captchaService;

    // Obtener todos los clientes
    public List<Customer> findAll() {
        return customersRepository.findAll();
    }

    // Buscar cliente por ID
    public Optional<Customer> findById(int id) {
        return customersRepository.findById(id);
    }

    // Guardar o actualizar cliente sin validación
    public Customer save(Customer customer) {
        return customersRepository.save(customer);
    }

    // Guardar con validación de Captcha
    public Customer saveWithCaptchaValidation(Customer customer, String captchaToken) {
        if (captchaToken == null || captchaToken.isEmpty()) {
            throw new IllegalArgumentException("Captcha token es requerido");
        }

        boolean isValid = captchaService.validateCaptcha(captchaToken);
        if (!isValid) {
            throw new IllegalArgumentException("Captcha inválido");
        }

        return customersRepository.save(customer);
    }

    // Eliminar cliente por ID
    public void deleteById(int id) {
        customersRepository.deleteById(id);
    }

    // Verificar existencia
    public boolean existsById(int id) {
        return customersRepository.existsById(id);
    }

    // Buscar por nombre
    public List<Customer> findByName(String name) {
        return customersRepository.findByNameContainingIgnoreCase(name);
    }

    // Buscar por email
    public List<Customer> findByEmail(String email) {
        return customersRepository.findByEmailContainingIgnoreCase(email);
    }
}
