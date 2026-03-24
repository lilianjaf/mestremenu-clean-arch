package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.AcessoNegadoConsultaUsuarioException;

public class ApenasDonoOuProprioUsuarioPodeConsultarRule implements ValidadorConsultaUsuarioRule {
    @Override
    public void validar(ConsultaUsuarioContext context) {
        if (context.usuarioLogado() == null) {
            return;
        }

        if (!context.isDonoOuProprioUsuario()) {
            throw new AcessoNegadoConsultaUsuarioException("Você não tem permissão para consultar este usuário");
        }
    }
}
