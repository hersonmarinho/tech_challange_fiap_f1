package br.com.fiap.horadoprato.horadoprato.repositories;

import br.com.fiap.horadoprato.horadoprato.entities.Usuario;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements  UsuarioRepository{

    private final JdbcClient jdbcClient;

    public UsuarioRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<Usuario> findByUsuario(String nome,String login) {
        return jdbcClient
                .sql("SELECT * FROM usuarios WHERE nome = :nome and login = :login")
                .param("nome", nome)
                .param("login", login)
                .query(Usuario.class)
                .optional();
    }

    @Override
    public Integer saveUsuario(Usuario usuario) {
        return this.jdbcClient
                .sql("INSERT INTO usuarios (nome, login, senha, email, tipo_usuario, endereco, data_ultima_alteracao) VALUES (:nome, :login, :senha, :email, :tipo_usuario, :endereco, :dataUltimaAlteracao)")
                .param("nome", usuario.getNome())
                .param("login", usuario.getLogin())
                .param("senha", usuario.getSenha())
                .param("email", usuario.getEmail())
                .param("tipo_usuario", usuario.getTipoUsuario())
                .param("endereco", usuario.getEndereco())
                .param("dataUltimaAlteracao", usuario.getDataUltimaAlteracao())
                .update();
    }

    @Override
    public Integer upateUsuario(Usuario usuario, String updateUsuario,String updateLogin) {
        return this.jdbcClient
                .sql("UPDATE usuarios SET senha = :senha, email = :email, tipo_usuario = :tipo_usuario, endereco = :endereco, data_ultima_alteracao = :data_ultima_alteracao WHERE nome = :nome AND login = :login")
                .param("nome", usuario.getNome() )
                .param("login", usuario.getLogin() )
                .param("senha", usuario.getSenha() )
                .param("email", usuario.getEmail() )
                .param("tipo_usuario", usuario.getTipoUsuario() )
                .param("endereco", usuario.getEndereco() )
                .param("data_ultima_alteracao", usuario.getDataUltimaAlteracao())
                .update();
    }

    @Override
    public Integer deleteUsuario(String nome,String login) {
        return this.jdbcClient
                .sql("DELETE FROM usuarios WHERE nome = :nome and login = :login")
                .param("nome",nome)
                .param("login",login)
                .update();
    }
}
