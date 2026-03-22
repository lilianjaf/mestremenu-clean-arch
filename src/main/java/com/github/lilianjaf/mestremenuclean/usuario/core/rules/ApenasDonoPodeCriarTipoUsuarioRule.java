package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.CriacaoTipoUsuarioNaoAutorizadaException;

public class ApenasDonoPodeCriarTipoUsuarioRule implements ValidadorPermissaoRule {
    @Override
    public void validar(UsuarioBase usuarioLogado) {
        if (!(usuarioLogado instanceof Dono)) {
            throw new CriacaoTipoUsuarioNaoAutorizadaException("Apenas usuários com o perfil de 'DONO' possuem permissão para cadastrar novos tipos de usuário.");
        }
    }
}
