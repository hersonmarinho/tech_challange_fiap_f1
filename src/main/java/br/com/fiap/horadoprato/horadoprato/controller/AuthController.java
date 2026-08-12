package br.com.fiap.horadoprato.horadoprato.controller;

import br.com.fiap.horadoprato.horadoprato.api.AuthApi; // Interface gerada pelo plugin OpenAPI
import br.com.fiap.horadoprato.horadoprato.dto.LoginDTO;
import br.com.fiap.horadoprato.horadoprato.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<String> login(LoginDTO loginDTO) {
        boolean valido = service.validarLogin(loginDTO);
        if (valido) {
            return ResponseEntity.ok("Login efetuado com sucesso.");
        }
        return ResponseEntity.status(401).body("Login ou senha inválidos.");
    }
}
