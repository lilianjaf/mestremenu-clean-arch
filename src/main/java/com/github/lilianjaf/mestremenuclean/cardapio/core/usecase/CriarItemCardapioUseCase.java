package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosCriacaoItemCardapio;

public interface CriarItemCardapioUseCase {
    ItemCardapio executar(DadosCriacaoItemCardapio dados);
}
