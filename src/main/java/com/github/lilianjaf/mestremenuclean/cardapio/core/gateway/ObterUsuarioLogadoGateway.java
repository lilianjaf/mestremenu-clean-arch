package com.github.lilianjaf.mestremenuclean.cardapio.core.gateway;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;

import java.util.Optional;

public interface ObterUsuarioLogadoGateway {
    Optional<Usuario> obterUsuarioLogado();
}