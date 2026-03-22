package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;

public interface CriarTipoUsuarioUsecase {
    TipoUsuario criar(String nome, TipoNativo tipoNativo);
}
