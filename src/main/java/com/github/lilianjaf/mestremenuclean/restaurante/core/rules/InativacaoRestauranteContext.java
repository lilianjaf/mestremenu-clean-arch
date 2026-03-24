package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

import java.util.Objects;

public record InativacaoRestauranteContext(
        UsuarioBase usuarioLogado,
        Restaurante restaurante
) {
    public boolean isUsuarioDonoDoRestaurante() {
        return usuarioLogado != null &&
               usuarioLogado.isDono() &&
               restaurante != null &&
               Objects.equals(usuarioLogado.getId(), restaurante.getIdDono());
    }

    public boolean isRestauranteAtivo() {
        return restaurante != null && restaurante.isAtivo();
    }
}
