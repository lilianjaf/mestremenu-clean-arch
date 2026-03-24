package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

public record BuscarRestauranteRuleContextDto(
        UsuarioBase usuarioLogado,
        Restaurante restaurante
) {
    public boolean isUsuarioAutenticado() {
        return this.usuarioLogado != null;
    }
}
