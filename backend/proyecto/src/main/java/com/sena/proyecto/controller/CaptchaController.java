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

    // Clase interna para respuesta más estructurada (también puedes moverla a otro archivo si prefieres)
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

    @PostMapping("/verify")
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
}
