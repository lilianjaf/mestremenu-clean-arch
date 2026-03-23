package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.InativacaoUsuarioNaoAutorizadaException;

public class ApenasDonoOuProprioUsuarioPodeInativarRule implements ValidadorInativacaoUsuarioRule {

    @Override
    public void validar(InativacaoUsuarioContext context) {
        boolean isDono = context.usuarioLogado() instanceof Dono;
        boolean isMesmoUsuario = context.usuarioLogado().getId().equals(context.usuarioAlvo().getId());

        if (!isDono && !isMesmoUsuario) {
            throw new InativacaoUsuarioNaoAutorizadaException("Apenas o próprio usuário ou um usuário do tipo DONO pode realizar esta inativação.");
        }
    }
}
