package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.EdicaoUsuarioNaoAutorizadaException;

public class ApenasDonoOuProprioUsuarioPodeEditarRule implements ValidadorPermissaoAtualizacaoUsuarioRule {
    @Override
    public void validar(AtualizacaoUsuarioContext context) {
        if (context.usuarioSendoEditado() == null) {
            return;
        }

        if (!context.isDonoOuProprioUsuario()) {
            throw new EdicaoUsuarioNaoAutorizadaException("Apenas o próprio usuário ou um usuário do tipo DONO pode realizar edições.");
        }
    }
}
