package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.AcessoNegadoExclusaoTipoUsuarioException;

public class ApenasDonoPodeDeletarTipoUsuarioRule implements ValidadorExclusaoTipoUsuarioRule {
    @Override
    public void validar(ExclusaoTipoUsuarioContext context) {
        if (!(context.usuarioLogado() instanceof Dono)) {
            throw new AcessoNegadoExclusaoTipoUsuarioException("Apenas usuários do tipo DONO podem excluir tipos de usuário.");
        }
    }
}
