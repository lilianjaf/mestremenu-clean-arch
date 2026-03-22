package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.TipoUsuarioInvalidoParaCadastroPublicoException;

public class UsuarioPublicoDeveSerTipoClienteRule implements ValidadorCriacaoUsuarioPublicoRule {

    @Override
    public void validar(CriacaoUsuarioPublicoContext context) {
        if (!"cliente".equalsIgnoreCase(context.nomeTipo())) {
            throw new TipoUsuarioInvalidoParaCadastroPublicoException("Apenas usuários do tipo CLIENTE podem ser criados via cadastro público.");
        }
    }
}
