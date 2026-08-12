package br.com.fiap.horadoprato.horadoprato.services;

import br.com.fiap.horadoprato.horadoprato.dto.SenhaUpdateDTO;
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
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
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
                        dto.getEndereco().getCidade(),
                        dto.getEndereco().getEstado(),
                        dto.getEndereco().getCep()
                ))
                .build();

        Usuario usuarioSalvo = repository.save(usuario);

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setLogin(usuarioSalvo.getLogin());
        response.setNome(usuarioSalvo.getNome());
        response.setEmail(usuarioSalvo.getEmail());

        return response;
    }

    @Transactional
    public void alterarSenha(String login, SenhaUpdateDTO dto) {
        Usuario usuario = buscarEntityPorlogin(login);

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
                    //response.setId(usuarioSalvo.getId());
                    response.setNome(usuarioSalvo.getNome());
                    response.setLogin(usuarioSalvo.getLogin());
                    response.setEmail(usuarioSalvo.getEmail());
                    return response;
                })
                .toList();
    }

    @Transactional
    public void atualizar(String login, UsuarioUpdateDTO dto) {
        Usuario usuario = buscarEntityPorlogin(login);

        usuario.setNome(dto.getNome());
        usuario.setTipoUsuario(TipoUsuario.valueOf(dto.getTipoUsuario().name()));
        usuario.setEndereco(new Endereco(
                dto.getEndereco().getLogradouro(),
                dto.getEndereco().getNumero(),
                dto.getEndereco().getComplemento(),
                dto.getEndereco().getCidade(),
                dto.getEndereco().getEstado(),
                dto.getEndereco().getCep()));

        repository.save(usuario);
    }

    @Transactional
    public void deletar(String login) {
        Usuario usuario = buscarEntityPorlogin(login);
        repository.delete(usuario);
    }

    private Usuario buscarEntityPorlogin(String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário  " + login + " não encontrado!"));
    }
}
