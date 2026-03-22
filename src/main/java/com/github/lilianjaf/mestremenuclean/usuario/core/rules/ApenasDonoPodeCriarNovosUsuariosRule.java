package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.AcessoNegadoCriacaoUsuarioException;

public class ApenasDonoPodeCriarNovosUsuariosRule implements ValidadorCriacaoUsuarioRule {

    @Override
    public void validar(CriacaoUsuarioContext context) {
        if (!(context.usuarioLogado() instanceof Dono)) {
            throw new AcessoNegadoCriacaoUsuarioException("Apenas usuários com o perfil de DONO podem criar outros usuários.");
        }
    }
}
