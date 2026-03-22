package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioNaoEncontradoException;

public class ConsultaUsuarioDeveExistirRule implements ValidadorConsultaUsuarioRule {
    @Override
    public void validar(UsuarioBase usuarioLogado, UsuarioBase usuarioBuscado) {
        if (usuarioBuscado == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado.");
        }
    }
}
