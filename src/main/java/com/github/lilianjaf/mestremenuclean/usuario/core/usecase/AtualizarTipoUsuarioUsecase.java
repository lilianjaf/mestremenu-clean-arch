package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegistroNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;

import java.util.UUID;

public class AtualizarTipoUsuarioUsecase {

    private final TipoUsuarioRepository repository;

    public AtualizarTipoUsuarioUsecase(TipoUsuarioRepository repository) {
        this.repository = repository;
    }

    public void atualizar(UUID id, String novoNome) {
        TipoUsuario tipo = repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Tipo de usuário não encontrado."));

        repository.findByNome(novoNome).ifPresent(tipoExistente -> {
            if (!tipoExistente.getId().equals(id)) {
                throw new RegraDeNegocioException("O nome '" + novoNome + "' já está em uso por outro tipo de usuário.");
            }
        });

        tipo.atualizarNome(novoNome);

        repository.salvar(tipo);
    }
}