package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.SenhaUsuarioNaoPodeSerVaziaException;

public class SenhaUsuarioPublicoNaoPodeSerVaziaRule implements ValidadorCriacaoUsuarioPublicoRule {
    @Override
    public void validar(CriacaoUsuarioPublicoContext context) {
        if (!context.isSenhaInformada()) {
            throw new SenhaUsuarioNaoPodeSerVaziaException("A senha não pode ser vazia");
        }
    }
}
