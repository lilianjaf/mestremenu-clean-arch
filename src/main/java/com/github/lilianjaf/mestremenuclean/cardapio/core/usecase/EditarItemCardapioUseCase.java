package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosAtualizacaoItemCardapio;

public interface EditarItemCardapioUseCase {
    ItemCardapio executar(DadosAtualizacaoItemCardapio dados);
}
