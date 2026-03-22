package com.github.lilianjaf.mestremenuclean.usuario.infra.controller;

import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.CriarUsuarioPublicoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/publico/usuarios")
public class UsuarioPublicoController {

    private final CriarUsuarioPublicoUseCase criarUsuarioPublicoUseCase;

    public UsuarioPublicoController(CriarUsuarioPublicoUseCase criarUsuarioPublicoUseCase) {
        this.criarUsuarioPublicoUseCase = criarUsuarioPublicoUseCase;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> criarUsuario(@RequestBody CriarUsuarioPublicoJson json) {
        UUID idGerado = criarUsuarioPublicoUseCase.criar(
                json.nome(),
                json.email(),
                json.login(),
                json.senha(),
                json.endereco().logradouro(),
                json.endereco().numero(),
                json.endereco().complemento(),
                json.endereco().bairro(),
                json.endereco().cidade(),
                json.endereco().cep(),
                json.endereco().uf()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", idGerado));
    }
}
