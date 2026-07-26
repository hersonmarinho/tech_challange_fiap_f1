package br.com.fiap.horadoprato.horadoprato.repositories;

import br.com.fiap.horadoprato.horadoprato.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findByUsuario(String usuario,String login);

    Integer saveUsuario(Usuario usuario);

    Integer upateUsuario(Usuario usuario, String updateUsuario,String updateLogin);

    Integer deleteUsuario(String usuario,String login);

}
