package com.github.lilianjaf.mestremenuclean.usuario.infra.controller;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.AtualizarTipoUsuarioUsecase;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.CriarTipoUsuarioUsecase;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.DeletarTipoUsuarioUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tipos-usuario")
public class TipoUsuarioController {

    private final CriarTipoUsuarioUsecase criarTipoUsuarioUsecase;
    private final AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase;
    private final DeletarTipoUsuarioUsecase deletarTipoUsuarioUsecase;
    private final UsuarioRepository usuarioRepository;

    public TipoUsuarioController(
            CriarTipoUsuarioUsecase criarTipoUsuarioUsecase,
            AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase,
            DeletarTipoUsuarioUsecase deletarTipoUsuarioUsecase,
            UsuarioRepository usuarioRepository) {
        this.criarTipoUsuarioUsecase = criarTipoUsuarioUsecase;
        this.atualizarTipoUsuarioUsecase = atualizarTipoUsuarioUsecase;
        this.deletarTipoUsuarioUsecase = deletarTipoUsuarioUsecase;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> criar(@RequestBody CriarTipoUsuarioJson json) {
        var usuarioLogado = getUsuarioLogado();
        TipoUsuario tipoCriado = criarTipoUsuarioUsecase.criar(usuarioLogado, json.nome(), json.tipoNativo());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", tipoCriado.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @RequestBody AtualizarTipoUsuarioJson json) {
        atualizarTipoUsuarioUsecase.atualizar(id, json.nome());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarTipoUsuarioUsecase.deletar(id);

        return ResponseEntity.noContent().build();
    }

    private com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase getUsuarioLogado() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioRepository.findByLogin(userDetails.getUsername()).orElseThrow();
    }
}