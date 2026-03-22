package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

public record AtualizacaoUsuarioContext(
        UsuarioBase usuarioSendoEditado,
        UsuarioBase usuarioComMesmoEmail
) {}
