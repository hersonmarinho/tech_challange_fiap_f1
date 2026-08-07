package br.com.fiap.horadoprato.horadoprato.controller;

import br.com.fiap.horadoprato.horadoprato.dto.SenhaUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioRequestDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioResponseDTO;
import br.com.fiap.horadoprato.horadoprato.dto.UsuarioUpdateDTO;
import br.com.fiap.horadoprato.horadoprato.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/v1")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dto));
    }

    @PatchMapping("/v1/{login}/senha")
    public ResponseEntity<Void> alterarSenha(
            @PathVariable String login,
            @RequestBody @Valid SenhaUpdateDTO dto) {
        service.alterarSenha(login, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/buscar")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @PatchMapping("/v1/{login}/atualizar")
    public ResponseEntity<Void> atualizar(
            //@PathVariable String login,
            @RequestBody @Valid UsuarioUpdateDTO dto) {
        service.atualizar(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/v1/{login}")
    public ResponseEntity<Void> deletar(@PathVariable String login) {
        service.deletar(login);
        return ResponseEntity.noContent().build();
    }
}