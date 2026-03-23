package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;

import java.util.UUID;

public class ExcluirRestauranteUseCaseImpl implements ExcluirRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;

    public ExcluirRestauranteUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public void executar(UUID id) {
        if (id == null) {
            throw new DomainException("ID do restaurante não pode ser nulo para exclusão.");
        }
        
        // Verifica se existe antes de deletar
        restauranteRepository.findById(id)
                .orElseThrow(() -> new DomainException("Restaurante não encontrado para exclusão."));

        restauranteRepository.deletar(id);
    }
}
