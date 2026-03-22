package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.EmailUsuarioJaEmUsoException;

public class EmailUsuarioDeveSerUnicoRule implements ValidadorAtualizacaoUsuarioRule {
    @Override
    public void validar(AtualizacaoUsuarioContext context) {
        if (context.usuarioComMesmoEmail() != null && 
            context.usuarioSendoEditado() != null &&
            !context.usuarioComMesmoEmail().getId().equals(context.usuarioSendoEditado().getId())) {
            throw new EmailUsuarioJaEmUsoException("E-mail já está em uso por outro usuário.");
        }
    }
}
