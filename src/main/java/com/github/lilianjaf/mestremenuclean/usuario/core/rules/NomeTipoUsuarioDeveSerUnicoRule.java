package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.NomeTipoUsuarioJaEmUsoException;

public class NomeTipoUsuarioDeveSerUnicoRule implements ValidadorAtualizacaoTipoUsuarioRule {

    @Override
    public void validar(AtualizacaoTipoUsuarioContext context) {
        if (context.isNomeJaEmUso()) {
            throw new NomeTipoUsuarioJaEmUsoException("O nome '" + context.getNomeTipoComMesmoNome() + "' já está em uso por outro tipo de usuário.");
        }
    }
}
