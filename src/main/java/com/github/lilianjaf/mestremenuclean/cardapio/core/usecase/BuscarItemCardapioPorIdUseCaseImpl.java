package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ItemCardapioRepository;

import java.util.UUID;

public class BuscarItemCardapioPorIdUseCaseImpl implements BuscarItemCardapioPorIdUseCase {

    private final ItemCardapioRepository itemCardapioRepository;

    public BuscarItemCardapioPorIdUseCaseImpl(ItemCardapioRepository itemCardapioRepository) {
        this.itemCardapioRepository = itemCardapioRepository;
    }

    @Override
    public ItemCardapio executar(UUID id) {
        return itemCardapioRepository.findById(id)
                .orElseThrow(() -> new CardapioException("Item do cardápio não encontrado."));
    }
}
