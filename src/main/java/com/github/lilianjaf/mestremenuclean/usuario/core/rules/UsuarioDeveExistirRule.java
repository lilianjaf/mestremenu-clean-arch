package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioNaoEncontradoException;

public class UsuarioDeveExistirRule implements ValidadorAtualizacaoUsuarioRule {
    @Override
    public void validar(AtualizacaoUsuarioContext context) {
        if (!context.isUsuarioSendoEditadoExistente()) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado.");
        }
    }
}
