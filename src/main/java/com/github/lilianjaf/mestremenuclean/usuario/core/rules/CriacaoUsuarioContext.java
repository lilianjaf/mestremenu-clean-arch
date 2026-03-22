package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

import java.util.function.BooleanSupplier;

public record CriacaoUsuarioContext(
        String nome,
        String email,
        String login,
        String senha,
        String nomeTipo,
        TipoNativo tipoNativo,
        BooleanSupplier emailJaExiste,
        BooleanSupplier loginJaExiste,
        UsuarioBase usuarioLogado
) {
}
