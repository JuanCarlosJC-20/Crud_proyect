package com.sena.proyecto.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${google.recaptcha.expected-hostname}")
    private String expectedHostname;

    public boolean verify(String responseToken) {
        if (responseToken == null || responseToken.trim().isEmpty()) {
            System.out.println("⚠️ Token vacío o nulo.");
            return false;
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("secret", recaptchaSecret);
        requestBody.add("response", responseToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.postForEntity(
                GOOGLE_RECAPTCHA_VERIFY_URL,
                requestEntity,
                (Class<Map<String, Object>>)(Class<?>) Map.class
        );

        Map<String, Object> body = response.getBody();
        if (body == null) {
            System.out.println("❌ Respuesta nula desde Google reCAPTCHA.");
            return false;
        }

        Boolean success = (Boolean) body.get("success");
        String hostname = (String) body.get("hostname");

        if (!Boolean.TRUE.equals(success)) {
            List<String> errors = (List<String>) body.get("error-codes");
            System.out.println("❌ Verificación fallida. Errores: " + errors);
            return false;
        }

        if (expectedHostname != null && !expectedHostname.equals(hostname)) {
            System.out.println("❌ Hostname no coincide. Esperado: " + expectedHostname + ", recibido: " + hostname);
            return false;
        }

        return true;
    }
}
