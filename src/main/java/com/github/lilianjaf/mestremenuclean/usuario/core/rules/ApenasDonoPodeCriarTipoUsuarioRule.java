package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.CriacaoTipoUsuarioNaoAutorizadaException;

public class ApenasDonoPodeCriarTipoUsuarioRule implements ValidadorCriacaoTipoUsuarioRule {
    @Override
    public void validar(CriacaoTipoUsuarioContext context) {
        if (!context.isUsuarioLogadoDono()) {
            throw new CriacaoTipoUsuarioNaoAutorizadaException("Apenas usuários com o perfil de 'DONO' possuem permissão para cadastrar novos tipos de usuário.");
        }
    }
}
