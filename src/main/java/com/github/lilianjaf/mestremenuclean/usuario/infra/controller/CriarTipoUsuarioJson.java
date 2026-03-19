package com.github.lilianjaf.mestremenuclean.usuario.infra.controller;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;

public record CriarTipoUsuarioJson(
        String nome,
        TipoNativo tipoNativo
) {
}