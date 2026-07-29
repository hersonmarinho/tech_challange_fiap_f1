package br.com.fiap.horadoprato.horadoprato.repositories;

import br.com.fiap.horadoprato.horadoprato.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    Optional<Usuario> findByLogin(String login);
}
