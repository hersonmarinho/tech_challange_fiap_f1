package br.com.fiap.horadoprato.horadoprato.controller;


import br.com.fiap.horadoprato.horadoprato.api.UsuariosApi;
import br.com.fiap.horadoprato.horadoprato.dto.SenhaUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioRequestDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioResponseDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioController implements UsuariosApi {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<UsuarioResponseDTO> cadastrar(UsuarioRequestDTO usuarioRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(usuarioRequestDTO));
    }

    @Override
    public ResponseEntity<Void> alterarSenha(String login, SenhaUpdateDTO senhaUpdateDTO) {
        service.alterarSenha(login, senhaUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNome(String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @Override
    public ResponseEntity<Void> atualizar(String login, UsuarioUpdateDTO usuarioUpdateDTO) {
        service.atualizar(login, usuarioUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deletar(String login) {
        service.deletar(login);
        return ResponseEntity.noContent().build();
    }
}
