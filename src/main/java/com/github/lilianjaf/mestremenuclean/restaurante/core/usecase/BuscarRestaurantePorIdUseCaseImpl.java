package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;

import java.util.UUID;

public class BuscarRestaurantePorIdUseCaseImpl implements BuscarRestaurantePorIdUseCase {

    private final RestauranteRepository restauranteRepository;

    public BuscarRestaurantePorIdUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public Restaurante executar(UUID id) {
        if (id == null) {
            throw new DomainException("ID do restaurante não pode ser nulo.");
        }
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new DomainException("Restaurante não encontrado."));
    }
}
