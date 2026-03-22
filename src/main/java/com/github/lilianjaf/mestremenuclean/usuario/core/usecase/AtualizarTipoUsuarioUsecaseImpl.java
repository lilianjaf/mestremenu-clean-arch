package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegistroNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorAtualizacaoTipoUsuarioRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorPermissaoRule;

import java.util.List;
import java.util.UUID;

public class AtualizarTipoUsuarioUsecaseImpl implements AtualizarTipoUsuarioUsecase {

    private final TipoUsuarioRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final TransactionGateway transactionGateway;
    private final List<ValidadorAtualizacaoTipoUsuarioRule> rules;
    private final List<ValidadorPermissaoRule> permissaoRules;

    public AtualizarTipoUsuarioUsecaseImpl(TipoUsuarioRepository repository, UsuarioRepository usuarioRepository, TransactionGateway transactionGateway, List<ValidadorAtualizacaoTipoUsuarioRule> rules, List<ValidadorPermissaoRule> permissaoRules) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.transactionGateway = transactionGateway;
        this.rules = rules;
        this.permissaoRules = permissaoRules;
    }

    @Override
    public void atualizar(String loginUsuarioLogado, UUID id, String novoNome) {
        UsuarioBase usuarioLogado = usuarioRepository.findByLogin(loginUsuarioLogado)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário logado não encontrado: " + loginUsuarioLogado));

        permissaoRules.forEach(rule -> rule.validar(usuarioLogado));

        transactionGateway.execute(() -> {
            TipoUsuario tipo = repository.findById(id)
                    .orElseThrow(() -> new TipoUsuarioNaoEncontradoException("Tipo de usuário não encontrado."));

            TipoUsuario tipoComMesmoNome = repository.findByNome(novoNome).orElse(null);

            rules.forEach(rule -> rule.validar(tipo, tipoComMesmoNome));

            tipo.atualizarNome(novoNome);

            repository.salvar(tipo);
        });
    }
}