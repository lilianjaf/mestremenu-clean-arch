package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;

public class CriarTipoUsuarioUsecase {

    private final TipoUsuarioRepository repository;

    public CriarTipoUsuarioUsecase(TipoUsuarioRepository repository) {
        this.repository = repository;
    }

    public TipoUsuario criar(String nome, TipoNativo tipoNativo) {
        if (repository.findByNome(nome).isPresent()) {
            throw new RegraDeNegocioException("Já existe um tipo de usuário cadastrado com o nome: " + nome);
        }

        TipoUsuario novoTipo = new TipoUsuario(nome, tipoNativo);

        return repository.salvar(novoTipo);
    }
}