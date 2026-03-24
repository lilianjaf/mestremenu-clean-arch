package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;

public record ExclusaoCardapioContext(
        Usuario usuarioLogado,
        Restaurante restaurante,
        Cardapio cardapioExistente
) {
}
