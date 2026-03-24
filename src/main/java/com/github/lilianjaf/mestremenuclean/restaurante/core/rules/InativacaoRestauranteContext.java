package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;

import java.util.Objects;

public record InativacaoRestauranteContext(
        Usuario usuarioLogado,
        Restaurante restaurante
) {
    public boolean isUsuarioAutenticado() {
        return usuarioLogado != null;
    }

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
