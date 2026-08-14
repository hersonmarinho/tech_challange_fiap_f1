package br.com.fiap.horadoprato.horadoprato.controller;

import br.com.fiap.horadoprato.horadoprato.dto.LoginDTO;
import br.com.fiap.horadoprato.horadoprato.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService service;

    @InjectMocks
    private AuthController controller;

    @Test
    void deveRetornar200QuandoLoginValido() {
        LoginDTO dto = new LoginDTO("user.login", "senha");
        when(service.validarLogin(dto)).thenReturn(true);

        ResponseEntity<String> response = controller.login(dto);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("Login efetuado com sucesso.", response.getBody());
    }

    @Test
    void deveRetornar401QuandoLoginInvalido() {
        LoginDTO dto = new LoginDTO("user.login", "senha");
        when(service.validarLogin(dto)).thenReturn(false);

        ResponseEntity<String> response = controller.login(dto);

        assertEquals(HttpStatusCode.valueOf(401), response.getStatusCode());
        assertEquals("Login ou senha inválidos.", response.getBody());
    }
}
