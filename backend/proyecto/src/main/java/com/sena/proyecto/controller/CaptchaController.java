package com.sena.proyecto.controller;

import com.sena.proyecto.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    // Endpoint que recibe el token del frontend y devuelve si es válido o no
    @PostMapping("/verify")
    public ResponseEntity<String> verifyCaptcha(@RequestParam("token") String token) {
        boolean isValid = captchaService.verify(token);

        if (isValid) {
            return ResponseEntity.ok("Captcha verificado correctamente.");
        } else {
            return ResponseEntity.badRequest().body("Fallo en la verificación del captcha.");
        }
    }
}
