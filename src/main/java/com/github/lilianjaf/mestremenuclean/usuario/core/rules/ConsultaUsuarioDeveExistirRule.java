package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioNaoEncontradoException;

public class ConsultaUsuarioDeveExistirRule implements ValidadorConsultaUsuarioRule {
    @Override
    public void validar(ConsultaUsuarioContext context) {
        if (!context.isUsuarioBuscadoExistente()) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado.");
        }
    }
}
