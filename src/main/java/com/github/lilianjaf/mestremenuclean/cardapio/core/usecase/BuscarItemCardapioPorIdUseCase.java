package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;

import java.util.UUID;

public interface BuscarItemCardapioPorIdUseCase {
    ItemCardapio executar(UUID id);
}
