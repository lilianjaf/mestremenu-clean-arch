package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.SenhaUsuarioNaoPodeSerVaziaException;

public class SenhaUsuarioPublicoNaoPodeSerVaziaRule implements ValidadorCriacaoUsuarioPublicoRule {
    @Override
    public void validar(CriacaoUsuarioPublicoContext context) {
        if (context.senha() == null || context.senha().isBlank()) {
            throw new SenhaUsuarioNaoPodeSerVaziaException("A senha não pode ser vazia");
        }
    }
}
