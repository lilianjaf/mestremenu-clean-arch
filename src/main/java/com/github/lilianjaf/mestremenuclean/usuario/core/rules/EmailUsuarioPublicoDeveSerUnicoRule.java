package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.EmailUsuarioJaEmUsoException;

public class EmailUsuarioPublicoDeveSerUnicoRule implements ValidadorCriacaoUsuarioPublicoRule {
    @Override
    public void validar(CriacaoUsuarioPublicoContext context) {
        if (context.emailJaExiste().getAsBoolean()) {
            throw new EmailUsuarioJaEmUsoException("E-mail já está em uso.");
        }
    }
}
