package br.com.fiap.horadoprato.horadoprato.controller;

import br.com.fiap.horadoprato.horadoprato.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{usuario}/{login}")
    public ResponseEntity<Optional<Usuario>> findUsuario(
            @PathVariable("usuario") String usuario,
            @PathVariable("login") String login
    ){
        logger.info("/usuario/"+usuario+"/"+login);
        var findUsuario = this.usuarioService.findByUsuario(usuario, login);
        return ResponseEntity.ok(findUsuario);
    }

    @PostMapping
    public  ResponseEntity<Void> saveUsuario(
            @RequestBody Usuario usuario
    ) {
        logger.info("POST -> /usuarios");
        this.usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{usuario}/{login}")
    public ResponseEntity<Void> upateUsuario(
            @PathVariable("usuario")  String updateUsuario,
            @PathVariable("login")   String updateLogin,
            @RequestBody Usuario usuario
    ) {
        logger.info("PUT -> /usuarios/" + updateUsuario + "/" + updateLogin);
        this.usuarioService.upateUsuario(usuario, updateUsuario, updateLogin);
        var status = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status.value()).build();
    }

    @DeleteMapping("/{usuario}/{login}")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable("usuario") String usuario,
            @PathVariable("login") String login
    ){
        logger.info("DELETE -> /usuario/" + usuario + "/" + login);
        this.usuarioService.deleteUsuario(usuario, login);
        return ResponseEntity.ok().build();
    }
}
