package com.github.lilianjaf.mestremenuclean.usuario.infra.controller;

import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.AtualizarTipoUsuarioUsecase;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.CriarTipoUsuarioUsecase;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.DeletarTipoUsuarioUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tipos-usuario")
public class TipoUsuarioController {

    private final CriarTipoUsuarioUsecase criarTipoUsuarioUsecase;
    private final AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase;
    private final DeletarTipoUsuarioUsecase deletarTipoUsuarioUsecase;

    public TipoUsuarioController(
            CriarTipoUsuarioUsecase criarTipoUsuarioUsecase,
            AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase,
            DeletarTipoUsuarioUsecase deletarTipoUsuarioUsecase) {
        this.criarTipoUsuarioUsecase = criarTipoUsuarioUsecase;
        this.atualizarTipoUsuarioUsecase = atualizarTipoUsuarioUsecase;
        this.deletarTipoUsuarioUsecase = deletarTipoUsuarioUsecase;
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody CriarTipoUsuarioJson json) {
        criarTipoUsuarioUsecase.criar(json.nome(), json.tipoNativo());

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
}