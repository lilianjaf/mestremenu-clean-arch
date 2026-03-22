package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

public interface ValidadorPermissaoRule {
    void validar(UsuarioBase usuarioLogado);
}
