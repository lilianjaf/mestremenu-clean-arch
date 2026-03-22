package com.github.lilianjaf.mestremenuclean.usuario.core.gateway;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

import java.util.Optional;

public interface ObterUsuarioLogadoGateway {
    Optional<UsuarioBase> obterUsuarioLogado();
}