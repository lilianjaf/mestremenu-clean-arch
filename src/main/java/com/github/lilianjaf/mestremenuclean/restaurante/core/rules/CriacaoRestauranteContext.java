package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.restaurante.core.dto.DadosCriacaoRestaurante;

public record CriacaoRestauranteContext(
        Usuario dono,
        DadosCriacaoRestaurante dados
) {
}
