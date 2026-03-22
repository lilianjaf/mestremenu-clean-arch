package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.*;
import com.github.lilianjaf.mestremenuclean.usuario.core.dto.DadosCriacaoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.CodificadorDeSenha;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorPermissaoRule;

import java.util.List;
import java.util.UUID;

public class CriarUsuarioUsecaseImpl implements CriarUsuarioUsecase {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final CodificadorDeSenha codificadorDeSenha;
    private final TransactionGateway transactionGateway;
    private final List<ValidadorPermissaoRule> rules;

    public CriarUsuarioUsecaseImpl(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository, CodificadorDeSenha codificadorDeSenha, TransactionGateway transactionGateway, List<ValidadorPermissaoRule> rules) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.codificadorDeSenha = codificadorDeSenha;
        this.transactionGateway = transactionGateway;
        this.rules = rules;
    }

    @Override
    public UUID criar(
            UsuarioBase usuarioLogado,
            String nome, String email, String login, String senhaPura,
            String nomeTipoDesejado, TipoNativo tipoNativoDesejado,
            String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf) {

        rules.forEach(rule -> rule.validar(usuarioLogado));

        return transactionGateway.execute(() -> {
            Endereco endereco = new Endereco(logradouro, numero, complemento, bairro, cidade, cep, uf);

            TipoUsuario tipoCustomizado = tipoUsuarioRepository.findByNome(nomeTipoDesejado)
                    .orElseGet(() -> {
                        if (tipoNativoDesejado == null) {
                            throw new RegraDeNegocioException("Tipo de usuário '" + nomeTipoDesejado + "' não existe. Informe o 'tipoNativo' (DONO ou CLIENTE) para criá-lo.");
                        }

                        TipoUsuario novoTipo = new TipoUsuario(nomeTipoDesejado, tipoNativoDesejado);
                        return tipoUsuarioRepository.salvar(novoTipo);
                    });

            String senhaCriptografada = codificadorDeSenha.codificar(senhaPura);
            var dadosCriacao = new DadosCriacaoUsuario(nome, email, login, senhaCriptografada, tipoCustomizado, endereco);
            UsuarioBase novoUsuario = UsuarioFactory.criar(dadosCriacao);

            UsuarioBase usuarioSalvo = usuarioRepository.salvar(novoUsuario);
            return usuarioSalvo.getId();
        });
    }
}