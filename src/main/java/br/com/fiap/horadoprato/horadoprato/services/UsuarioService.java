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
        if (repository.existsByLogin(dto.login())) {
            throw new LoginJaCadastradoException("Este login já esta cadastrado no sistema!");
        }
        if (repository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException("Este e-mail já esta cadastrado no sistema!");
        }

        Usuario usuario = Usuario.builder()
                .login(dto.login())
                .nome(dto.nome())
                .email(dto.email())
                .senha(CriptografiaUtil.encriptar(dto.senha())) // TODO: precisamos considerar ocultar esse dado
                .tipoUsuario(dto.tipoUsuario())
                .endereco(dto.endereco())
                .build();

        return UsuarioResponseDTO.fromEntity(repository.save(usuario));
    }

    @Transactional
    public void alterarSenha(String  login, SenhaUpdateDTO dto) {
        Usuario usuario = buscarEntityPorlogin(login);

        if (!CriptografiaUtil.decriptar(usuario.getSenha()).equals(dto.senhaAtual())) {
            throw new CadastrodeSenhaException("A senha atual informada está incorreta!");
        } else if (CriptografiaUtil.decriptar(usuario.getSenha()).equals(dto.novaSenha())) {
            throw new CadastrodeSenhaException("A nova senha informada é igual a senha anterior!");
        } else {
            usuario.setSenha(CriptografiaUtil.encriptar(dto.novaSenha()));
            repository.save(usuario);
        }
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
    }

    /*@Transactional(readOnly = true)
    public boolean validarLogin(LoginDTO dto) {
        return repository.findByLogin(dto.login())
                .map(u -> u.getSenha().equals(CriptografiaUtil.encriptar(dto.senha())))
                .orElse(false);
    }*/

    @Transactional
    public void atualizar(UsuarioUpdateDTO dto) {

        Usuario usuario = buscarEntityPorlogin(dto.login());

        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setTipoUsuario(dto.tipoUsuario());
        usuario.setEndereco(dto.endereco());

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