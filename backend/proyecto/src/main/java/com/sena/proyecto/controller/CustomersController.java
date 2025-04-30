package com.sena.proyecto.controller;

import com.sena.proyecto.model.Customer;
import com.sena.proyecto.model.CustomerRequest;
import com.sena.proyecto.service.CustomersService;
import com.sena.proyecto.service.CaptchaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @Autowired
    private CaptchaService captchaService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> customers = customersService.findAll();
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable int id) {
        return customersService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerRequest request) {
        if (request.getCaptchaToken() == null || request.getCaptchaToken().isEmpty()) {
            return ResponseEntity.badRequest().body("❌ captchaToken es requerido");
        }

        boolean validCaptcha = captchaService.validateCaptcha(request.getCaptchaToken());
        if (!validCaptcha) {
            return ResponseEntity.badRequest().body("❌ Captcha inválido");
        }

        try {
            Customer saved = customersService.save(request.getCustomer());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error interno al guardar el cliente");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Customer customer) {
        if (!customersService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customer.setId(id);
        return ResponseEntity.ok(customersService.save(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, @RequestParam(required = false) String captchaToken) {
        if (captchaToken == null || !captchaService.validateCaptcha(captchaToken)) {
            return ResponseEntity.badRequest().body("❌ Captcha inválido o ausente");
        }

        if (!customersService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        customersService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
