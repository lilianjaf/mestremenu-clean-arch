package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

public interface ValidadorPermissaoAtualizacaoUsuarioRule {
    void validar(UsuarioBase usuarioLogado, UsuarioBase usuarioSendoEditado);
}
