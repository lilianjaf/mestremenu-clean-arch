package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;

public record AtualizarRestauranteRuleContextDto(
        Usuario usuarioLogado,
        Restaurante restaurante
) {
    public boolean isUsuarioAutenticado() {
        return usuarioLogado != null;
    }

    public boolean isUsuarioLogadoProprietarioDoRestaurante() {
        return restaurante.getIdDono().equals(usuarioLogado.getId());
    }

    public boolean restaurantePossuiDono() {
        return restaurante.getIdDono() != null;
    }
}
