package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.CardapioRepository;

import java.util.List;
import java.util.UUID;

public class BuscarCardapioPorRestauranteUseCaseImpl implements BuscarCardapioPorRestauranteUseCase {

    private final CardapioRepository cardapioRepository;

    public BuscarCardapioPorRestauranteUseCaseImpl(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public List<Cardapio> executar(UUID idRestaurante) {
        return cardapioRepository.buscarPorIdRestaurante(idRestaurante);
    }
}
