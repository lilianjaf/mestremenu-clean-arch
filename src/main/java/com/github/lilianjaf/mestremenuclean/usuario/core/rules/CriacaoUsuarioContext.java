package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;

public record CriacaoUsuarioContext(
        String nome,
        String email,
        String login,
        String nomeTipo,
        TipoNativo tipoNativo
) {
}
