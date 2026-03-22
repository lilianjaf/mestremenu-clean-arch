package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioNaoAutenticadoException;

public class UsuarioDeveEstarAutenticadoRule implements ValidadorConsultaUsuarioRule, ValidadorPermissaoAtualizacaoUsuarioRule, ValidadorPermissaoRule {
    @Override
    public void validar(UsuarioBase usuarioLogado, UsuarioBase usuarioBuscado) {
        validar(usuarioLogado);
    }

    @Override
    public void validar(UsuarioBase usuarioLogado) {
        if (usuarioLogado == null) {
            throw new UsuarioNaoAutenticadoException("Usuário não autenticado");
        }
    }
}
