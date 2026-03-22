package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.AcessoNegadoException;

public class ValidarPermissaoDonoRule implements ValidadorPermissaoRule {

    @Override
    public void validar(UsuarioBase usuarioLogado) {
        if (!(usuarioLogado instanceof Dono)) {
            throw new AcessoNegadoException("Apenas um usuário 'dono' pode realizar esta operação.");
        }
    }
}
