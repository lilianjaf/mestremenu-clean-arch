package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.restaurante.core.dto.DadosCriacaoRestaurante;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

public record CriacaoRestauranteContext(
        UsuarioBase usuarioLogado,
        Usuario dono,
        DadosCriacaoRestaurante dados
) {
    public boolean isUsuarioLogadoTipoDono() {
        return usuarioLogado != null && usuarioLogado.isDono();
    }

    public boolean hasDonoVinculado() {
        return dono != null;
    }
}
