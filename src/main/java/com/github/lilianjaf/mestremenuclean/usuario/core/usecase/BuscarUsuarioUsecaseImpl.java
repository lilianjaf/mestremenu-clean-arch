package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.dto.UsuarioOutput;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorConsultaUsuarioRule;

import java.util.List;
import java.util.UUID;

public class BuscarUsuarioUsecaseImpl implements BuscarUsuarioUsecase {
    private final UsuarioRepository repository;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;
    private final List<ValidadorConsultaUsuarioRule> permissaoRules;
    private final List<ValidadorConsultaUsuarioRule> rules;

    public BuscarUsuarioUsecaseImpl(UsuarioRepository repository,
                                    ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                    List<ValidadorConsultaUsuarioRule> permissaoRules,
                                    List<ValidadorConsultaUsuarioRule> rules) {
        this.repository = repository;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
        this.permissaoRules = permissaoRules;
        this.rules = rules;
    }

    @Override
    public UsuarioOutput buscarPorId(UUID id) {
        UsuarioBase usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado().orElse(null);
        UsuarioBase usuarioBuscado = repository.findById(id).orElse(null);

        permissaoRules.forEach(rule -> rule.validar(usuarioLogado, usuarioBuscado));
        rules.forEach(rule -> rule.validar(usuarioLogado, usuarioBuscado));

        return new UsuarioOutput(
                usuarioBuscado.getId(),
                usuarioBuscado.getNome(),
                usuarioBuscado.getEmail(),
                usuarioBuscado.getLogin(),
                usuarioBuscado.getTipoCustomizado().getNome(),
                usuarioBuscado.getAtivo()
        );
    }

    @Override
    public UsuarioBase buscarEntidade(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
    }
}