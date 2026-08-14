package br.com.fiap.horadoprato.horadoprato.services;

import br.com.fiap.horadoprato.horadoprato.dto.LoginDTO;
import br.com.fiap.horadoprato.horadoprato.infra.security.CriptografiaUtil;
import br.com.fiap.horadoprato.horadoprato.model.Usuario;
import br.com.fiap.horadoprato.horadoprato.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private AuthService service;

    @Test
    void deveValidarLoginComSenhaCorreta() {
        LoginDTO dto = new LoginDTO("user.login", "senha123");
        Usuario usuario = Usuario.builder()
                .login("user.login")
                .senha(CriptografiaUtil.encriptar("senha123"))
                .build();
        when(repository.findByLogin("user.login")).thenReturn(Optional.of(usuario));

        boolean valido = service.validarLogin(dto);

        assertTrue(valido);
    }

    @Test
    void deveRetornarFalsoQuandoSenhaIncorreta() {
        LoginDTO dto = new LoginDTO("user.login", "senhaErrada");
        Usuario usuario = Usuario.builder()
                .login("user.login")
                .senha(CriptografiaUtil.encriptar("senha123"))
                .build();
        when(repository.findByLogin("user.login")).thenReturn(Optional.of(usuario));

        boolean valido = service.validarLogin(dto);

        assertFalse(valido);
    }

    @Test
    void deveRetornarFalsoQuandoUsuarioNaoExiste() {
        LoginDTO dto = new LoginDTO("nao.existe", "senha123");
        when(repository.findByLogin("nao.existe")).thenReturn(Optional.empty());

        boolean valido = service.validarLogin(dto);

        assertFalse(valido);
    }
}
