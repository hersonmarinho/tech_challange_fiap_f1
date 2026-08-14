package br.com.fiap.horadoprato.horadoprato.api;

import br.com.fiap.horadoprato.horadoprato.dto.LoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthApiTest {

    @Test
    void deveRetornarRequestVazioPorPadrao() {
        AuthApi api = new AuthApi() {
        };

        assertTrue(api.getRequest().isEmpty());
    }

    @Test
    void deveRetornarNotImplementedNoLoginPadrao() {
        AuthApi api = new AuthApi() {
        };

        ResponseEntity<String> response = api.login(new LoginDTO("login", "senha"));

        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }
}
