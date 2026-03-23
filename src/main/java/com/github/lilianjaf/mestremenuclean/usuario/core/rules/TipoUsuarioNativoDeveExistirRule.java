package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.TipoUsuarioInvalidoException;

public class TipoUsuarioNativoDeveExistirRule implements ValidadorCriacaoUsuarioRule {

    @Override
    public void validar(CriacaoUsuarioContext context) {
        if (context.tipoNativo() == null ) {
            throw new TipoUsuarioInvalidoException("Tipo de usuário nativo inválido.");
        }
    }
}
