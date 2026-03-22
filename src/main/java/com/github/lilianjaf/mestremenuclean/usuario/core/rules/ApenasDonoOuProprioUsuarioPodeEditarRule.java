package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.EdicaoUsuarioNaoAutorizadaException;

public class ApenasDonoOuProprioUsuarioPodeEditarRule implements ValidadorPermissaoAtualizacaoUsuarioRule {
    @Override
    public void validar(UsuarioBase usuarioLogado, UsuarioBase usuarioSendoEditado) {
        if (usuarioSendoEditado == null) {
            return;
        }

        boolean isDono = usuarioLogado instanceof Dono;
        boolean isProprioUsuario = usuarioLogado.getId().equals(usuarioSendoEditado.getId());

        if (!isDono && !isProprioUsuario) {
            throw new EdicaoUsuarioNaoAutorizadaException("Apenas o próprio usuário ou um usuário do tipo DONO pode realizar edições.");
        }
    }
}
