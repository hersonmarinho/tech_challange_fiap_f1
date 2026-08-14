package br.com.fiap.horadoprato.horadoprato.controller;

import br.com.fiap.horadoprato.horadoprato.dto.SenhaUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioRequestDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioResponseDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    @Test
    void deveCadastrarComStatus201() {
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO().id("id-1").nome("Carlos");
        when(service.cadastrar(request)).thenReturn(responseDTO);

        ResponseEntity<UsuarioResponseDTO> response = controller.cadastrar(request);

        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals("id-1", response.getBody().getId());
    }

    @Test
    void deveAlterarSenhaComStatus204() {
        SenhaUpdateDTO dto = new SenhaUpdateDTO("antiga", "nova");

        ResponseEntity<Void> response = controller.alterarSenha("id-1", dto);

        verify(service).alterarSenhaPorId("id-1", dto);
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
    }

    @Test
    void deveBuscarPorNomeComStatus200() {
        List<UsuarioResponseDTO> lista = List.of(new UsuarioResponseDTO().id("id-1"));
        when(service.buscarPorNome("car")).thenReturn(lista);

        ResponseEntity<List<UsuarioResponseDTO>> response = controller.buscarPorNome("car");

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void deveAtualizarComStatus204() {
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO();

        ResponseEntity<Void> response = controller.atualizar("id-1", dto);

        verify(service).atualizarPorId("id-1", dto);
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
    }

    @Test
    void deveDeletarComStatus204() {
        ResponseEntity<Void> response = controller.deletar("id-1");

        verify(service).deletarPorId("id-1");
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
    }
}
