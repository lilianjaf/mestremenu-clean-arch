package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.AcessoNegadoConsultaUsuarioException;

public class ApenasDonoOuProprioUsuarioPodeConsultarRule implements ValidadorConsultaUsuarioRule {
    @Override
    public void validar(UsuarioBase usuarioLogado, UsuarioBase usuarioBuscado) {
        if (usuarioLogado == null) {
            return; // Outra regra deve tratar isso
        }

        boolean isDono = usuarioLogado instanceof Dono;
        boolean isMesmoUsuario = usuarioLogado.getId().equals(usuarioBuscado.getId());

        if (!isDono && !isMesmoUsuario) {
            throw new AcessoNegadoConsultaUsuarioException("Você não tem permissão para consultar este usuário");
        }
    }
}
