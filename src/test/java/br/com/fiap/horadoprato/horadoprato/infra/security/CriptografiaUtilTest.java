package br.com.fiap.horadoprato.horadoprato.infra.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CriptografiaUtilTest {

    @Test
    void deveCriptografarEDecriptarComSucesso() {
        String original = "senha-super-segura";

        String criptografada = CriptografiaUtil.encriptar(original);
        String decriptada = CriptografiaUtil.decriptar(criptografada);

        assertNotEquals(original, criptografada);
        assertEquals(original, decriptada);
    }

    @Test
    void deveRetornarNullQuandoEncriptarNull() {
        assertNull(CriptografiaUtil.encriptar(null));
    }

    @Test
    void deveRetornarNullQuandoDecriptarNull() {
        assertNull(CriptografiaUtil.decriptar(null));
    }

    @Test
    void deveLancarErroQuandoTextoInvalidoParaDecriptar() {
        assertThrows(RuntimeException.class, () -> CriptografiaUtil.decriptar("texto-invalido"));
    }
}
