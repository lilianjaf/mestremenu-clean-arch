package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

public interface CriarTipoUsuarioUsecase {
    TipoUsuario criar(UsuarioBase usuarioLogado, String nome, TipoNativo tipoNativo);
}
