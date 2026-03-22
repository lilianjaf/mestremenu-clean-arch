package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.*;
import com.github.lilianjaf.mestremenuclean.usuario.core.dto.DadosCriacaoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.CodificadorDeSenha;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.CriacaoUsuarioContext;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorCriacaoUsuarioRule;

import java.util.List;
import java.util.UUID;

public class CriarUsuarioPublicoUseCaseImpl implements CriarUsuarioPublicoUseCase {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final TransactionGateway transactionGateway;
    private final CodificadorDeSenha codificadorDeSenha;
    private final List<ValidadorCriacaoUsuarioRule> rules;

    public CriarUsuarioPublicoUseCaseImpl(
            UsuarioRepository usuarioRepository,
            TipoUsuarioRepository tipoUsuarioRepository,
            List<ValidadorCriacaoUsuarioRule> rules,
            TransactionGateway transactionGateway,
            CodificadorDeSenha codificadorDeSenha) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.rules = rules;
        this.transactionGateway = transactionGateway;
        this.codificadorDeSenha = codificadorDeSenha;
    }

    @Override
    public UUID criar(
            String nome, String email, String login, String senhaPura,
            String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf) {

        return transactionGateway.execute(() -> {
            Endereco endereco = new Endereco(logradouro, numero, complemento, bairro, cidade, cep, uf);
            TipoUsuario tipoCustomizado = tipoUsuarioRepository.findByNome("cliente").orElseThrow();

            String senhaCriptografada = codificadorDeSenha.codificar(senhaPura);
            var dadosCriacao = new DadosCriacaoUsuario(nome, email, login, senhaCriptografada, tipoCustomizado, endereco);
            UsuarioBase novoUsuario = UsuarioFactory.criar(dadosCriacao);

            var context = new CriacaoUsuarioContext(nome, email, login, tipoCustomizado.getNome(), tipoCustomizado.getTipoNativo());
            rules.forEach(rule -> rule.validar(context));

            UsuarioBase usuarioSalvo = usuarioRepository.salvar(novoUsuario);
            return usuarioSalvo.getId();
        });
    }
}
