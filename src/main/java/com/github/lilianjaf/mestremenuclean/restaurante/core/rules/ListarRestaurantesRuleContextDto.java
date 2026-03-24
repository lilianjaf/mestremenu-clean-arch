package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;

public record ListarRestaurantesRuleContextDto(
        Usuario usuarioLogado
) {
    public boolean isUsuarioAutenticado() {
        return this.usuarioLogado != null;
    }
}
