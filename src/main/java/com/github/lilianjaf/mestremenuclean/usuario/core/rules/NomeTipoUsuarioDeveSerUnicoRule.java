package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.NomeTipoUsuarioJaEmUsoException;

public class NomeTipoUsuarioDeveSerUnicoRule implements ValidadorAtualizacaoTipoUsuarioRule {

    @Override
    public void validar(TipoUsuario tipoAtual, TipoUsuario tipoComMesmoNome) {
        if (tipoComMesmoNome != null && !tipoComMesmoNome.getId().equals(tipoAtual.getId())) {
            throw new NomeTipoUsuarioJaEmUsoException("O nome '" + tipoComMesmoNome.getNome() + "' já está em uso por outro tipo de usuário.");
        }
    }
}
