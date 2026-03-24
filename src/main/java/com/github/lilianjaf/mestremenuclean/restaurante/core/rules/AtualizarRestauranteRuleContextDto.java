package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

public record AtualizarRestauranteRuleContextDto(
        UsuarioBase usuarioLogado,
        Restaurante restaurante
) {
    public boolean isUsuarioLogadoProprietarioDoRestaurante() {
        return restaurante.getIdDono().equals(usuarioLogado.getId());
    }

    public boolean restaurantePossuiDono() {
        return restaurante.getIdDono() != null;
    }
}
