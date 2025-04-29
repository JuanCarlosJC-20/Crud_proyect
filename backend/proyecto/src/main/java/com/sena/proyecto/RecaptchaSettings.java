package com.sena.proyecto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.recaptcha")
public class RecaptchaSettings {

    private String secret;
    private String expectedHostname;  // Nuevo atributo

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getExpectedHostname() {
        return expectedHostname;  // Getter para expectedHostname
    }

    public void setExpectedHostname(String expectedHostname) {
        this.expectedHostname = expectedHostname;  // Setter para expectedHostname
    }
}
