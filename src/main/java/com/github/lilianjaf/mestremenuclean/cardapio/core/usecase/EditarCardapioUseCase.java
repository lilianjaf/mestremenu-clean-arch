package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosAtualizacaoCardapio;

public interface EditarCardapioUseCase {
    Cardapio executar(DadosAtualizacaoCardapio dados);
}
