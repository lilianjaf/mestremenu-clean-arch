package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;

import java.util.UUID;

public class InativarUsuarioUsecase {
    private final BuscarUsuarioUsecase buscarUsuarioUsecase;
    private final UsuarioRepository repository;

    public InativarUsuarioUsecase(BuscarUsuarioUsecase buscarUsuarioUsecase, UsuarioRepository repository) {
        this.buscarUsuarioUsecase = buscarUsuarioUsecase;
        this.repository = repository;
    }

    public void inativar(UUID id) {
        UsuarioBase usuario = buscarUsuarioUsecase.buscarEntidade(id);

        usuario.desativar();

        repository.salvar(usuario);
    }
}