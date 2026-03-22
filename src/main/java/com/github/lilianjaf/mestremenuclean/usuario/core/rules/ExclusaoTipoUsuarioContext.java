package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

import java.util.Optional;
import java.util.function.BooleanSupplier;

public record ExclusaoTipoUsuarioContext(
        Optional<TipoUsuario> tipoUsuarioASerDeletado,
        BooleanSupplier estaEmUso,
        UsuarioBase usuarioLogado
) {
}
