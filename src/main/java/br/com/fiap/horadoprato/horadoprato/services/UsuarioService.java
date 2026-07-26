package br.com.fiap.horadoprato.horadoprato.services;

import br.com.fiap.horadoprato.horadoprato.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> findByUsuario(String usuario, String login){
        return usuarioRepository.findByUsuario(usuario,login);
    }

    public void saveUsuario(Usuario usuario){
        var save = this.usuarioRepository.saveUsuario(usuario);
        Assert.state(save == 1, "Erro ao salvar o usuário de nome " + usuario.getNome() + " e login " + usuario.getLogin());
    }

    public void upateUsuario(Usuario usuario, String updateUsuario,String updateLogin){
        var update = this.usuarioRepository.upateUsuario(usuario,updateUsuario,updateLogin);
        if (update == 0) {
            throw new RuntimeException("Usuário com o nome "  + usuario.getNome() + " e login " + usuario.getLogin() + "não encontrado.");
        }
    }

    public void deleteUsuario(String usuario,String login){
        var delete = this.usuarioRepository.deleteUsuario(usuario,login);
        if (delete == 0) {
            throw new RuntimeException("Usuário com o nome "  + usuario + " e login " + login + "não encontrado.");
        }

    }
}
