package br.com.fiap.horadoprato.horadoprato.api;

import br.com.fiap.horadoprato.horadoprato.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuariosApiTest {

    @Test
    void deveRetornarNotImplementedNosMetodosBasicos() {
        UsuariosApi api = new UsuariosApi() {
        };

        assertEquals(HttpStatus.NOT_IMPLEMENTED, api.alterarSenha("id-1", new SenhaUpdateDTO("a", "b")).getStatusCode());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, api.atualizar("id-1", new UsuarioUpdateDTO()).getStatusCode());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, api.deletar("id-1").getStatusCode());
    }

    @Test
    void deveDefinirExemploNoBuscarPorNomeQuandoAcceptJson() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/json");
        MockHttpServletResponse response = new MockHttpServletResponse();
        NativeWebRequest webRequest = new ServletWebRequest(request, response);

        UsuariosApi api = new UsuariosApi() {
            @Override
            public Optional<NativeWebRequest> getRequest() {
                return Optional.of(webRequest);
            }
        };

        ResponseEntity<List<UsuarioResponseDTO>> result = api.buscarPorNome("carlos");

        assertEquals(HttpStatus.NOT_IMPLEMENTED, result.getStatusCode());
        assertEquals("application/json;charset=UTF-8", response.getHeader("Content-Type"));
        assertTrue(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8).contains("carlos_alberto"));
    }

    @Test
    void deveDefinirExemploNoCadastrarQuandoAcceptJson() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/json");
        MockHttpServletResponse response = new MockHttpServletResponse();
        NativeWebRequest webRequest = new ServletWebRequest(request, response);

        UsuariosApi api = new UsuariosApi() {
            @Override
            public Optional<NativeWebRequest> getRequest() {
                return Optional.of(webRequest);
            }
        };

        ResponseEntity<UsuarioCadastroResponseDTO> result = api.cadastrar(new UsuarioRequestDTO());

        assertEquals(HttpStatus.NOT_IMPLEMENTED, result.getStatusCode());
        assertEquals("application/json;charset=UTF-8", response.getHeader("Content-Type"));
        assertTrue(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8).contains("id"));
    }
}
