package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioPossuiRestauranteAtivoException;

public class UsuarioSemRestauranteVinculadoRule implements ValidadorInativacaoUsuarioRule {

    @Override
    public void validar(InativacaoUsuarioContext context) {
        if (context.isUsuarioAlvoDonoComRestaurantes()) {
            throw new UsuarioPossuiRestauranteAtivoException("Usuário possui restaurante(s) ativo(s) vinculado(s) e não pode ser inativado.");
        }
    }
}
