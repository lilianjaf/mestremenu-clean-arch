package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

public record InativacaoUsuarioContext(
        UsuarioBase usuarioLogado,
        UsuarioBase usuarioAlvo
) {
}
