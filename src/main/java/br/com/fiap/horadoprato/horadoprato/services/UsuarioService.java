package br.com.fiap.horadoprato.horadoprato.services;

import br.com.fiap.horadoprato.horadoprato.dto.EnderecoDTO;
import br.com.fiap.horadoprato.horadoprato.dto.SenhaUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.dto.TipoUsuarioEnum;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioCadastroResponseDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioRequestDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioResponseDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.dto.exception.EmailJaCadastradoException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.LoginJaCadastradoException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.CadastrodeSenhaException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.UsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.infra.security.CriptografiaUtil;
import br.com.fiap.horadoprato.horadoprato.model.Endereco;
import br.com.fiap.horadoprato.horadoprato.model.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.model.Usuario;
import br.com.fiap.horadoprato.horadoprato.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UsuarioCadastroResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if (repository.existsByLogin(dto.getLogin())) {
            throw new LoginJaCadastradoException("Este login já esta cadastrado no sistema!");
        }

        if (repository.existsByEmail(dto.getEmail())) {
            throw new EmailJaCadastradoException("Este e-mail já esta cadastrado no sistema!");
        }

        Usuario usuario = Usuario.builder()
                .login(dto.getLogin())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(CriptografiaUtil.encriptar(dto.getSenha()))
                .tipoUsuario(TipoUsuario.valueOf(dto.getTipoUsuario().name()))
                .endereco(new Endereco(
                        dto.getEndereco().getLogradouro(),
                        dto.getEndereco().getNumero(),
                        dto.getEndereco().getComplemento(),
                        dto.getEndereco().getBairro(),
                        dto.getEndereco().getCidade(),
                        dto.getEndereco().getEstado(),
                        dto.getEndereco().getCep()
                ))
                .build();

        Usuario usuarioSalvo = repository.save(usuario);

        UsuarioCadastroResponseDTO response = new UsuarioCadastroResponseDTO();
        response.setId(usuarioSalvo.getId());
        response.setNome(usuarioSalvo.getNome());
        response.setEmail(usuarioSalvo.getEmail());
        response.setTipoUsuario(TipoUsuarioEnum.fromValue(usuarioSalvo.getTipoUsuario().name()));

        return response;
    }

    @Transactional
    public void alterarSenhaPorId(String id, SenhaUpdateDTO dto) {
        Usuario usuario = buscarEntityPorId(id);

        if (!CriptografiaUtil.decriptar(usuario.getSenha()).equals(dto.getSenhaAntiga())) {
            throw new CadastrodeSenhaException("A senha atual informada está incorreta!");
        } else if (CriptografiaUtil.decriptar(usuario.getSenha()).equals(dto.getNovaSenha())) {
            throw new CadastrodeSenhaException("A nova senha informada é igual a senha anterior!");
        } else {
            usuario.setSenha(CriptografiaUtil.encriptar(dto.getNovaSenha()));
            repository.save(usuario);
        }
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(usuarioSalvo -> {
                    UsuarioResponseDTO response = new UsuarioResponseDTO();
                    response.setId(usuarioSalvo.getId());
                    response.setNome(usuarioSalvo.getNome());
                    response.setLogin(usuarioSalvo.getLogin());
                    response.setEmail(usuarioSalvo.getEmail());
                    
                    if (usuarioSalvo.getTipoUsuario() != null) {
                        response.setTipoUsuario(TipoUsuarioEnum.fromValue(usuarioSalvo.getTipoUsuario().name()));
                    }
                    
                    if (usuarioSalvo.getEndereco() != null) {
                        EnderecoDTO enderecoDTO = new EnderecoDTO()
                                .logradouro(usuarioSalvo.getEndereco().getLogradouro())
                                .numero(usuarioSalvo.getEndereco().getNumero())
                                .complemento(usuarioSalvo.getEndereco().getComplemento())
                                .bairro(usuarioSalvo.getEndereco().getBairro())
                                .cidade(usuarioSalvo.getEndereco().getCidade())
                                .estado(usuarioSalvo.getEndereco().getEstado())
                                .cep(usuarioSalvo.getEndereco().getCep());
                        response.setEndereco(enderecoDTO);
                    }
                    return response;
                })
                .toList();
    }

    @Transactional
    public void atualizarPorId(String id, UsuarioUpdateDTO dto) {
        Usuario usuario = buscarEntityPorId(id);

        if (dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }

        if (dto.getTipoUsuario() != null) {
            usuario.setTipoUsuario(TipoUsuario.valueOf(dto.getTipoUsuario().name()));
        }

        if (dto.getEndereco() != null) {
            usuario.setEndereco(new Endereco(
                    dto.getEndereco().getLogradouro(),
                    dto.getEndereco().getNumero(),
                    dto.getEndereco().getComplemento(),
                    dto.getEndereco().getBairro(),
                    dto.getEndereco().getCidade(),
                    dto.getEndereco().getEstado(),
                    dto.getEndereco().getCep()));
        }

        repository.save(usuario);
    }

    @Transactional
    public void deletarPorId(String id) {
        Usuario usuario = buscarEntityPorId(id);
        repository.delete(usuario);
    }

    private Usuario buscarEntityPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado!"));
    }
}
