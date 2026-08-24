package br.com.fiap.horadoprato.horadoprato.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsuarioTest {

    @Test
    void deveAtualizarDataUltimaAlteracaoTruncadaEmSegundos() {
        Usuario usuario = new Usuario();

        usuario.atualizarDataAlteracao();

        assertNotNull(usuario.getDataUltimaAlteracao());
        assertEquals(0, usuario.getDataUltimaAlteracao().getNano());
    }
}
