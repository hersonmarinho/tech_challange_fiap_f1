package br.com.fiap.horadoprato.horadoprato.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DtoCoverageTest {

    @Test
    void deveCobrirLoginDTO() {
        LoginDTO dto = new LoginDTO("login", "senha");
        dto.setLogin("novo-login");
        dto.setSenha("nova-senha");

        assertEquals("novo-login", dto.getLogin());
        assertEquals("nova-senha", dto.getSenha());
        assertTrue(dto.toString().contains("senha: *"));
        assertEquals(dto, new LoginDTO().login("novo-login").senha("nova-senha"));
    }

    @Test
    void deveCobrirSenhaUpdateDTO() {
        SenhaUpdateDTO dto = new SenhaUpdateDTO("antiga", "nova");
        dto.setSenhaAntiga("antiga-2");
        dto.setNovaSenha("nova-2");

        assertEquals("antiga-2", dto.getSenhaAntiga());
        assertEquals("nova-2", dto.getNovaSenha());
        assertTrue(dto.toString().contains("senhaAntiga: *"));
        assertEquals(dto, new SenhaUpdateDTO().senhaAntiga("antiga-2").novaSenha("nova-2"));
    }

    @Test
    void deveCobrirEnderecoDTO() {
        EnderecoDTO dto = new EnderecoDTO("Rua A", "10", "Centro", "São Paulo", "SP", "01000-000")
                .complemento("Apto 12");
        dto.setBairro("Bairro B");

        assertEquals("Rua A", dto.getLogradouro());
        assertEquals("10", dto.getNumero());
        assertEquals("Apto 12", dto.getComplemento());
        assertEquals("Bairro B", dto.getBairro());
        assertEquals("São Paulo", dto.getCidade());
        assertEquals("SP", dto.getEstado());
        assertEquals("01000-000", dto.getCep());
        assertTrue(dto.toString().contains("logradouro"));
    }

    @Test
    void deveCobrirUsuarioRequestDTO() {
        EnderecoDTO endereco = new EnderecoDTO("Rua A", "10", "Centro", "São Paulo", "SP", "01000-000");
        UsuarioRequestDTO dto = new UsuarioRequestDTO(
                "Carlos",
                "carlos.login",
                "senha123",
                "carlos@teste.com",
                TipoUsuarioEnum.CLIENTE,
                endereco
        );

        dto.setNome("Carlos Silva");
        dto.setTipoUsuario(TipoUsuarioEnum.DONO_RESTAURANTE);

        assertEquals("Carlos Silva", dto.getNome());
        assertEquals("carlos.login", dto.getLogin());
        assertEquals("senha123", dto.getSenha());
        assertEquals("carlos@teste.com", dto.getEmail());
        assertEquals(TipoUsuarioEnum.DONO_RESTAURANTE, dto.getTipoUsuario());
        assertEquals(endereco, dto.getEndereco());
        assertTrue(dto.toString().contains("senha: *"));
    }

    @Test
    void deveCobrirUsuarioResponseDTOEUsuarioUpdateDTO() {
        UsuarioResponseDTO response = new UsuarioResponseDTO()
                .id("id-1")
                .nome("Carlos")
                .login("carlos.login")
                .email("carlos@teste.com");

        assertEquals("id-1", response.getId());
        assertTrue(response.toString().contains("id"));

        UsuarioUpdateDTO update = new UsuarioUpdateDTO()
                .nome("Carlos Novo")
                .tipoUsuario(TipoUsuarioEnum.CLIENTE)
                .endereco(new EnderecoDTO("Rua B", "20", "Centro", "São Paulo", "SP", "01111-000"));

        assertEquals("Carlos Novo", update.getNome());
        assertEquals(TipoUsuarioEnum.CLIENTE, update.getTipoUsuario());
        assertTrue(update.toString().contains("tipoUsuario"));
    }

    @Test
    void deveCobrirTipoUsuarioEnum() {
        assertEquals("CLIENTE", TipoUsuarioEnum.CLIENTE.getValue());
        assertEquals(TipoUsuarioEnum.DONO_RESTAURANTE, TipoUsuarioEnum.fromValue("DONO_RESTAURANTE"));
        assertThrows(IllegalArgumentException.class, () -> TipoUsuarioEnum.fromValue("INVALIDO"));
    }
}
