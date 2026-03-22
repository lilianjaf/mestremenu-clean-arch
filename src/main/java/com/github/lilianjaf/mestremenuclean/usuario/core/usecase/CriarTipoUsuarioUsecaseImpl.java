package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorPermissaoRule;

import java.util.List;

public class CriarTipoUsuarioUsecaseImpl implements CriarTipoUsuarioUsecase {

    private final TipoUsuarioRepository repository;
    private final TransactionGateway transactionGateway;
    private final List<ValidadorPermissaoRule> rules;

    public CriarTipoUsuarioUsecaseImpl(TipoUsuarioRepository repository, TransactionGateway transactionGateway, List<ValidadorPermissaoRule> rules) {
        this.repository = repository;
        this.transactionGateway = transactionGateway;
        this.rules = rules;
    }

    @Override
    public TipoUsuario criar(UsuarioBase usuarioLogado, String nome, TipoNativo tipoNativo) {
        rules.forEach(rule -> rule.validar(usuarioLogado));

        return transactionGateway.execute(() -> {
            if (repository.findByNome(nome).isPresent()) {
                throw new RegraDeNegocioException("Já existe um tipo de usuário cadastrado com o nome: " + nome);
            }

            TipoUsuario novoTipo = new TipoUsuario(nome, tipoNativo);

            return repository.salvar(novoTipo);
        });
    }
}