package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Endereco;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioLogadoNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.AtualizacaoUsuarioContext;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorAtualizacaoUsuarioRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorPermissaoAtualizacaoUsuarioRule;

import java.util.List;
import java.util.UUID;

public class AtualizarUsuarioUsecaseImpl implements AtualizarUsuarioUsecase {
    private final UsuarioRepository repository;
    private final TransactionGateway transactionGateway;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;
    private final List<ValidadorPermissaoAtualizacaoUsuarioRule> permissaoRules;
    private final List<ValidadorAtualizacaoUsuarioRule> rules;

    public AtualizarUsuarioUsecaseImpl(UsuarioRepository repository,
                                       TransactionGateway transactionGateway,
                                       ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                       List<ValidadorPermissaoAtualizacaoUsuarioRule> permissaoRules,
                                       List<ValidadorAtualizacaoUsuarioRule> rules) {
        this.repository = repository;
        this.transactionGateway = transactionGateway;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
        this.permissaoRules = permissaoRules;
        this.rules = rules;
    }

    @Override
    public void atualizarComEndereco(
            UUID id, String novoNome, String novoEmail,
            String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf) {

        UsuarioBase usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new UsuarioLogadoNaoEncontradoException("Usuário logado não encontrado"));

        transactionGateway.execute(() -> {
            UsuarioBase usuarioSendoEditado = repository.findById(id).orElse(null);

            permissaoRules.forEach(rule -> rule.validar(usuarioLogado, usuarioSendoEditado));

            UsuarioBase usuarioComMesmoEmail = repository.findByEmail(novoEmail).orElse(null);

            AtualizacaoUsuarioContext context = new AtualizacaoUsuarioContext(
                    usuarioSendoEditado,
                    usuarioComMesmoEmail
            );

            rules.forEach(rule -> rule.validar(context));

            usuarioSendoEditado.atualizarDadosBasicos(novoNome, novoEmail);

            Endereco novoEndereco = new Endereco(logradouro, numero, complemento, bairro, cidade, cep, uf);
            usuarioSendoEditado.atualizarEndereco(novoEndereco);

            repository.salvar(usuarioSendoEditado);
        });
    }

    @Override
    public void atualizarSemEndereco(UUID id, String novoNome, String novoEmail) {
        UsuarioBase usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new UsuarioLogadoNaoEncontradoException("Usuário logado não encontrado"));

        transactionGateway.execute(() -> {
            UsuarioBase usuarioSendoEditado = repository.findById(id).orElse(null);

            permissaoRules.forEach(rule -> rule.validar(usuarioLogado, usuarioSendoEditado));

            UsuarioBase usuarioComMesmoEmail = repository.findByEmail(novoEmail).orElse(null);

            AtualizacaoUsuarioContext context = new AtualizacaoUsuarioContext(
                    usuarioSendoEditado,
                    usuarioComMesmoEmail
            );

            rules.forEach(rule -> rule.validar(context));

            usuarioSendoEditado.atualizarDadosBasicos(novoNome, novoEmail);
            repository.salvar(usuarioSendoEditado);
        });
    }
}