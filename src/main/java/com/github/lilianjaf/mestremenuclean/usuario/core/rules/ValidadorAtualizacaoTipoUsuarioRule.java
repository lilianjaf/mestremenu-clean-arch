package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;

public interface ValidadorAtualizacaoTipoUsuarioRule {
    void validar(TipoUsuario tipoAtual, TipoUsuario tipoComMesmoNome);
}
