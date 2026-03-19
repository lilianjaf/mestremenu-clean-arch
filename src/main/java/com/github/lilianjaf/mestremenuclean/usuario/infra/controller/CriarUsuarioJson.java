package com.github.lilianjaf.mestremenuclean.usuario.infra.controller;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;

public record CriarUsuarioJson(
        String nome,
        String email,
        String login,
        String senha,
        String nomeTipoDesejado,
        TipoNativo tipoNativo,
        EnderecoJson endereco
) {
}