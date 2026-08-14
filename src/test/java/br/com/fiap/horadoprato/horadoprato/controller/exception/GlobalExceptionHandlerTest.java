package br.com.fiap.horadoprato.horadoprato.controller.exception;

import br.com.fiap.horadoprato.horadoprato.dto.exception.CadastrodeSenhaException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void deveRetornarConflitoParaExcecoesDeCadastro() {
        ProblemDetail detail = handler.handleCadastroJaRealizado(new CadastrodeSenhaException("mensagem"));

        assertEquals(HttpStatus.CONFLICT.value(), detail.getStatus());
        assertEquals("Conflito de cadastro", detail.getTitle());
        assertEquals("mensagem", detail.getDetail());
        assertNotNull(detail.getProperties().get("timestamp"));
    }

    @Test
    void deveRetornarNotFoundParaUsuarioNaoEncontrado() {
        ProblemDetail detail = handler.handleUsuarioNaoEncontrado(new UsuarioNaoEncontradoException("não encontrado"));

        assertEquals(HttpStatus.NOT_FOUND.value(), detail.getStatus());
        assertEquals("Cadastro não encontrado", detail.getTitle());
        assertEquals("não encontrado", detail.getDetail());
        assertNotNull(detail.getProperties().get("timestamp"));
    }

    @Test
    void deveRetornarErroInternoParaExcecaoGenerica() {
        ProblemDetail detail = handler.handleExceptionGenerica(new RuntimeException("falha interna"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), detail.getStatus());
        assertEquals("Erro nao esperado!", detail.getTitle());
        assertEquals("falha interna", detail.getDetail());
        assertNotNull(detail.getProperties().get("timestamp"));
    }
}
