package br.com.fiap.horadoprato.horadoprato.services;

import br.com.fiap.horadoprato.horadoprato.dto.LoginDTO;
import br.com.fiap.horadoprato.horadoprato.infra.security.CriptografiaUtil;
import br.com.fiap.horadoprato.horadoprato.repositories.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UsuarioRepository repository;

    public AuthService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public boolean validarLogin(LoginDTO dto) {
        return repository.findByLogin(dto.getLogin())
                .map(u -> u.getSenha().equals(CriptografiaUtil.encriptar(dto.getSenha())))
                .orElse(false);
    }

}
