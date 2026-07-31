package br.com.fiap.horadoprato.horadoprato.services;

import br.com.fiap.horadoprato.horadoprato.dto.LoginDTO;
import br.com.fiap.horadoprato.horadoprato.dto.SenhaUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioRequestDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioResponseDTO;
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
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado no sistema.");
        }
        if (repository.existsByLogin(dto.login())) {
            throw new IllegalArgumentException("Login já está em uso.");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .login(dto.login())
                .senha(CriptografiaUtil.encriptar(dto.senha())) // TODO: precisamos considerar ocultar esse dado
                .tipoUsuario(dto.tipoUsuario())
                .endereco(dto.endereco())
                .build();

        return UsuarioResponseDTO.fromEntity(repository.save(usuario));
    }

    @Transactional
    public void alterarSenha(Long id, SenhaUpdateDTO dto) {
        Usuario usuario = buscarEntityPorId(id);

        if (!CriptografiaUtil.decriptar(usuario.getSenha()).equals(dto.senhaAtual())) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        usuario.setSenha(CriptografiaUtil.encriptar(dto.novaSenha()));
        repository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean validarLogin(LoginDTO dto) {
        return repository.findByLogin(dto.login())
                .map(u -> u.getSenha().equals(dto.senha()))
                .orElse(false);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = buscarEntityPorId(id);
        repository.delete(usuario);
    }

    private Usuario buscarEntityPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + id));
    }
}