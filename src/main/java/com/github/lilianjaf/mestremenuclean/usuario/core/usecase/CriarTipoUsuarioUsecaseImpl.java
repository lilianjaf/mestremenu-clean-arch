package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegistroNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorPermissaoRule;

import java.util.List;

public class CriarTipoUsuarioUsecaseImpl implements CriarTipoUsuarioUsecase {

    private final TipoUsuarioRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final TransactionGateway transactionGateway;
    private final List<ValidadorPermissaoRule> rules;

    public CriarTipoUsuarioUsecaseImpl(TipoUsuarioRepository repository, UsuarioRepository usuarioRepository, TransactionGateway transactionGateway, List<ValidadorPermissaoRule> rules) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.transactionGateway = transactionGateway;
        this.rules = rules;
    }

    @Override
    public TipoUsuario criar(String loginUsuarioLogado, String nome, TipoNativo tipoNativo) {
        UsuarioBase usuarioLogado = usuarioRepository.findByLogin(loginUsuarioLogado)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário logado não encontrado: " + loginUsuarioLogado));

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