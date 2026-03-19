package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.dto.UsuarioOutput;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;

import java.util.UUID;

public class BuscarUsuarioUsecase {
    private final UsuarioRepository repository;

    public BuscarUsuarioUsecase(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioOutput buscarPorId(UUID id) {
        UsuarioBase usuario = buscarEntidade(id);

        return new UsuarioOutput(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoCustomizado().getNome(),
                usuario.getAtivo()
        );
    }

    public UsuarioBase buscarEntidade(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException("Usuário não encontrado."));
    }
}