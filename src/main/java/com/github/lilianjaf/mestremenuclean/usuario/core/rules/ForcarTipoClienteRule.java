package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.TipoUsuarioInvalidoException;

public class ForcarTipoClienteRule implements ValidadorCriacaoUsuarioRule {
    @Override
    public void validar(CriacaoUsuarioContext context) {
        if (context.tipoNativo() != TipoNativo.CLIENTE) {
            throw new TipoUsuarioInvalidoException("O fluxo de criação pública permite apenas o tipo CLIENTE.");
        }
    }
}
