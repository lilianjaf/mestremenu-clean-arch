package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosCriacaoItemCardapio;

public record CriacaoItemCardapioContext(
        Usuario usuarioLogado,
        Restaurante restaurante,
        DadosCriacaoItemCardapio dados
) {
}
