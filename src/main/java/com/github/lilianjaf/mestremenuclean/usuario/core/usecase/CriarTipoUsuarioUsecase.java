package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;

public class CriarTipoUsuarioUsecase {

    private final TipoUsuarioRepository repository;
    private final TransactionGateway transactionGateway;

    public CriarTipoUsuarioUsecase(TipoUsuarioRepository repository, TransactionGateway transactionGateway) {
        this.repository = repository;
        this.transactionGateway = transactionGateway;
    }

    public TipoUsuario criar(String nome, TipoNativo tipoNativo) {
        return transactionGateway.execute(() -> {
            if (repository.findByNome(nome).isPresent()) {
                throw new RegraDeNegocioException("Já existe um tipo de usuário cadastrado com o nome: " + nome);
            }

            TipoUsuario novoTipo = new TipoUsuario(nome, tipoNativo);

            return repository.salvar(novoTipo);
        });
    }
}