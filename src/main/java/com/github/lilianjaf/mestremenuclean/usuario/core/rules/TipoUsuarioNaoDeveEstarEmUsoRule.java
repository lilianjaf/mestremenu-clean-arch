package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.TipoUsuarioEmUsoException;

public class TipoUsuarioNaoDeveEstarEmUsoRule implements ValidadorExclusaoTipoUsuarioRule {
    @Override
    public void validar(ExclusaoTipoUsuarioContext context) {
        if (context.isTipoUsuarioEmUso()) {
            throw new TipoUsuarioEmUsoException("Não é possível excluir o tipo " + context.getNomeTipoUsuario() + " pois existem usuários vinculados a ele.");
        }
    }
}
