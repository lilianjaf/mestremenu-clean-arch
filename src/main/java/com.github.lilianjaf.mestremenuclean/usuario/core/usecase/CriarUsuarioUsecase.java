package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.*;
import com.github.lilianjaf.mestremenuclean.usuario.core.dto.DadosCriacaoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.CodificadorDeSenha;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;

public class CriarUsuarioUsecase {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final CodificadorDeSenha codificadorDeSenha;

    public CriarUsuarioUsecase(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository, CodificadorDeSenha codificadorDeSenha) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.codificadorDeSenha = codificadorDeSenha;
    }

    public void criar(
            String nome, String email, String login, String senhaPura,
            String nomeTipoDesejado, TipoNativo tipoNativoDesejado, // Recebe o TipoNativo caso precise criar
            String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf) {

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

        usuarioRepository.salvar(novoUsuario);
    }
}