package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosCriacaoCardapio;

public interface CriarCardapioUseCase {
    Cardapio executar(DadosCriacaoCardapio dados);
}
