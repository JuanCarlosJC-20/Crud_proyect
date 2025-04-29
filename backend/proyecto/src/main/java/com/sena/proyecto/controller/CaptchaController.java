package com.sena.proyecto.controller;

import com.sena.proyecto.model.Product;
import com.sena.proyecto.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    // Clase interna para respuesta más estructurada
    public static class CaptchaResponse {
        private boolean success;
        private String message;

        public CaptchaResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    // Endpoint para verificar el captcha, este ya lo tienes
    @PostMapping("/captcha/verify")
    public ResponseEntity<CaptchaResponse> verifyCaptcha(@RequestParam("token") String token) {
        if (token == null || token.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                new CaptchaResponse(false, "Token del captcha vacío o inválido.")
            );
        }

        boolean isValid = captchaService.verify(token);

        if (isValid) {
            return ResponseEntity.ok(new CaptchaResponse(true, "Captcha verificado correctamente."));
        } else {
            return ResponseEntity.badRequest().body(
                new CaptchaResponse(false, "Fallo en la verificación del captcha.")
            );
        }
    }

    // Endpoint que procesa los datos del formulario
    @PostMapping("/procesar-formulario")
    public ResponseEntity<String> procesarFormulario(
            @RequestParam String captchaToken, 
            @RequestBody  Product product) {
        
        // Verificar el CAPTCHA antes de procesar los datos
        if (!captchaService.verify(captchaToken)) {
            return ResponseEntity.badRequest().body("Captcha inválido. No se procesan los datos.");
        }

        // Si el CAPTCHA es válido, procesamos los datos
        // Aquí iría la lógica para guardar los datos del formulario, por ejemplo:
        // datosService.guardar(datosFormulario);
        
        return ResponseEntity.ok("Formulario procesado correctamente.");
    }
}
