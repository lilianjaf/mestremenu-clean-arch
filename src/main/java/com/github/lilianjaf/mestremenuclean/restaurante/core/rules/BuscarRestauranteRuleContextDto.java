package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;

public record BuscarRestauranteRuleContextDto(
        Usuario usuarioLogado,
        Restaurante restaurante
) {
    public boolean isUsuarioAutenticado() {
        return this.usuarioLogado != null;
    }
}
