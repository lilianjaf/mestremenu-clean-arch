package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.LoginUsuarioJaEmUsoException;

public class LoginUsuarioPublicoDeveSerUnicoRule implements ValidadorCriacaoUsuarioPublicoRule {
    @Override
    public void validar(CriacaoUsuarioPublicoContext context) {
        if (context.loginJaExiste()) {
            throw new LoginUsuarioJaEmUsoException("Login já está em uso.");
        }
    }
}
