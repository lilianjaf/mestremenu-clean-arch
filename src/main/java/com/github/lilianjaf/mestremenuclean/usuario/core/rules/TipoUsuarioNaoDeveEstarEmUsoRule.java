package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.TipoUsuarioEmUsoException;

public class TipoUsuarioNaoDeveEstarEmUsoRule implements ValidadorExclusaoTipoUsuarioRule {
    @Override
    public void validar(ExclusaoTipoUsuarioContext context) {
        if (context.estaEmUso().getAsBoolean()) {
            String nome = context.tipoUsuarioASerDeletado()
                    .map(tipo -> "'" + tipo.getNome() + "' ")
                    .orElse("");
            throw new TipoUsuarioEmUsoException("Não é possível excluir o tipo " + nome + " pois existem usuários vinculados a ele.");
        }
    }
}
