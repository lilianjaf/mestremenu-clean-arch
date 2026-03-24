package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

public record ListarRestaurantesRuleContextDto(
        UsuarioBase usuarioLogado
) {
    public boolean isUsuarioAutenticado() {
        return this.usuarioLogado != null;
    }
}
