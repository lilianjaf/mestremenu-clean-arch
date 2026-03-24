package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.CredenciaisJaEmUsoException;

public class EmailELoginDevemSerUnicosRule implements ValidadorCriacaoUsuarioRule {

    @Override
    public void validar(CriacaoUsuarioContext context) {
        if (context.isEmailJaCadastrado()) {
            throw new CredenciaisJaEmUsoException("O e-mail informado já está em uso.");
        }
        if (context.isLoginJaCadastrado()) {
            throw new CredenciaisJaEmUsoException("O login informado já está em uso.");
        }
    }
}
