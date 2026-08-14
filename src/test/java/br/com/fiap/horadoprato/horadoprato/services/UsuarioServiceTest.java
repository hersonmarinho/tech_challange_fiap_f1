package br.com.fiap.horadoprato.horadoprato.services;

import br.com.fiap.horadoprato.horadoprato.dto.EnderecoDTO;
import br.com.fiap.horadoprato.horadoprato.dto.SenhaUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.dto.TipoUsuarioEnum;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioRequestDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioResponseDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.dto.exception.CadastrodeSenhaException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.EmailJaCadastradoException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.LoginJaCadastradoException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.UsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.infra.security.CriptografiaUtil;
import br.com.fiap.horadoprato.horadoprato.model.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.model.Usuario;
import br.com.fiap.horadoprato.horadoprato.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    @Test
    void deveLancarErroQuandoLoginJaExisteAoCadastrar() {
        UsuarioRequestDTO dto = criarUsuarioRequestDTO();
        when(repository.existsByLogin(dto.getLogin())).thenReturn(true);

        assertThrows(LoginJaCadastradoException.class, () -> service.cadastrar(dto));
        verify(repository, never()).save(org.mockito.ArgumentMatchers.any(Usuario.class));
    }

    @Test
    void deveLancarErroQuandoEmailJaExisteAoCadastrar() {
        UsuarioRequestDTO dto = criarUsuarioRequestDTO();
        when(repository.existsByLogin(dto.getLogin())).thenReturn(false);
        when(repository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(EmailJaCadastradoException.class, () -> service.cadastrar(dto));
        verify(repository, never()).save(org.mockito.ArgumentMatchers.any(Usuario.class));
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        UsuarioRequestDTO dto = criarUsuarioRequestDTO();
        Usuario salvo = Usuario.builder()
                .id("id-1")
                .nome(dto.getNome())
                .login(dto.getLogin())
                .email(dto.getEmail())
                .senha(CriptografiaUtil.encriptar(dto.getSenha()))
                .tipoUsuario(TipoUsuario.CLIENTE)
                .build();

        when(repository.existsByLogin(dto.getLogin())).thenReturn(false);
        when(repository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(repository.save(org.mockito.ArgumentMatchers.any(Usuario.class))).thenReturn(salvo);

        UsuarioResponseDTO response = service.cadastrar(dto);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture());
        Usuario enviado = captor.getValue();
        assertEquals(dto.getNome(), enviado.getNome());
        assertEquals(dto.getLogin(), enviado.getLogin());
        assertEquals(dto.getEmail(), enviado.getEmail());
        assertNotEquals(dto.getSenha(), enviado.getSenha());
        assertEquals(dto.getSenha(), CriptografiaUtil.decriptar(enviado.getSenha()));
        assertEquals("id-1", response.getId());
    }

    @Test
    void deveLancarErroQuandoSenhaAntigaIncorreta() {
        Usuario usuario = Usuario.builder()
                .id("id-1")
                .senha(CriptografiaUtil.encriptar("senha-correta"))
                .build();
        when(repository.findById("id-1")).thenReturn(Optional.of(usuario));

        SenhaUpdateDTO dto = new SenhaUpdateDTO("senha-errada", "nova-senha");

        assertThrows(CadastrodeSenhaException.class, () -> service.alterarSenhaPorId("id-1", dto));
    }

    @Test
    void deveLancarErroQuandoNovaSenhaIgualAntiga() {
        Usuario usuario = Usuario.builder()
                .id("id-1")
                .senha(CriptografiaUtil.encriptar("senha-correta"))
                .build();
        when(repository.findById("id-1")).thenReturn(Optional.of(usuario));

        SenhaUpdateDTO dto = new SenhaUpdateDTO("senha-correta", "senha-correta");

        assertThrows(CadastrodeSenhaException.class, () -> service.alterarSenhaPorId("id-1", dto));
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        Usuario usuario = Usuario.builder()
                .id("id-1")
                .senha(CriptografiaUtil.encriptar("senha-antiga"))
                .build();
        when(repository.findById("id-1")).thenReturn(Optional.of(usuario));

        SenhaUpdateDTO dto = new SenhaUpdateDTO("senha-antiga", "senha-nova");
        service.alterarSenhaPorId("id-1", dto);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture());
        assertEquals("senha-nova", CriptografiaUtil.decriptar(captor.getValue().getSenha()));
    }

    @Test
    void deveLancarErroAoAlterarSenhaQuandoUsuarioNaoEncontrado() {
        when(repository.findById("id-inexistente")).thenReturn(Optional.empty());
        SenhaUpdateDTO dto = new SenhaUpdateDTO("senha-antiga", "senha-nova");

        assertThrows(UsuarioNaoEncontradoException.class, () -> service.alterarSenhaPorId("id-inexistente", dto));
    }

    @Test
    void deveRetornarUsuariosMapeadosPorNome() {
        Usuario u1 = Usuario.builder().id("1").nome("Carlos").login("carlos").email("carlos@teste.com").build();
        Usuario u2 = Usuario.builder().id("2").nome("Carla").login("carla").email("carla@teste.com").build();
        when(repository.findByNomeContainingIgnoreCase("car")).thenReturn(List.of(u1, u2));

        List<UsuarioResponseDTO> response = service.buscarPorNome("car");

        assertEquals(2, response.size());
        assertEquals("1", response.getFirst().getId());
        assertEquals("carla", response.get(1).getLogin());
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        Usuario usuario = Usuario.builder().id("id-1").build();
        when(repository.findById("id-1")).thenReturn(Optional.of(usuario));

        UsuarioUpdateDTO dto = new UsuarioUpdateDTO()
                .nome("Nome Atualizado")
                .tipoUsuario(TipoUsuarioEnum.DONO_RESTAURANTE)
                .endereco(criarEnderecoDTO());

        service.atualizarPorId("id-1", dto);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture());
        Usuario salvo = captor.getValue();
        assertEquals("Nome Atualizado", salvo.getNome());
        assertEquals(TipoUsuario.DONO_RESTAURANTE, salvo.getTipoUsuario());
        assertEquals("São Paulo", salvo.getEndereco().getCidade());
    }

    @Test
    void deveLancarErroAoAtualizarQuandoUsuarioNaoEncontrado() {
        when(repository.findById("id-inexistente")).thenReturn(Optional.empty());

        UsuarioUpdateDTO dto = new UsuarioUpdateDTO()
                .nome("Nome Atualizado")
                .tipoUsuario(TipoUsuarioEnum.DONO_RESTAURANTE)
                .endereco(criarEnderecoDTO());

        assertThrows(UsuarioNaoEncontradoException.class, () -> service.atualizarPorId("id-inexistente", dto));
    }

    @Test
    void deveDeletarUsuarioComSucesso() {
        Usuario usuario = Usuario.builder().id("id-1").build();
        when(repository.findById("id-1")).thenReturn(Optional.of(usuario));

        service.deletarPorId("id-1");

        verify(repository).delete(usuario);
    }

    @Test
    void deveLancarErroQuandoUsuarioNaoEncontrado() {
        when(repository.findById("id-inexistente")).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> service.deletarPorId("id-inexistente"));
    }

    private UsuarioRequestDTO criarUsuarioRequestDTO() {
        return new UsuarioRequestDTO(
                "Carlos",
                "carlos.login",
                "senha123",
                "carlos@teste.com",
                TipoUsuarioEnum.CLIENTE,
                criarEnderecoDTO()
        );
    }

    private EnderecoDTO criarEnderecoDTO() {
        return new EnderecoDTO()
                .logradouro("Rua A")
                .numero("100")
                .complemento("Apto 1")
                .bairro("Centro")
                .cidade("São Paulo")
                .estado("SP")
                .cep("01000-000");
    }
}
