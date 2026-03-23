package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.dto.DadosAtualizacaoRestaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;

import java.util.UUID;

public class AtualizarRestauranteUseCaseImpl implements AtualizarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;

    public AtualizarRestauranteUseCaseImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public Restaurante executar(UUID id, DadosAtualizacaoRestaurante dados) {
        if (id == null) {
            throw new DomainException("ID do restaurante não pode ser nulo para atualização.");
        }

        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new DomainException("Restaurante não encontrado."));

        restaurante.atualizar(
                dados.nome(),
                dados.endereco(),
                dados.tipoCozinha(),
                dados.horarioFuncionamento()
        );

        return restauranteRepository.salvar(restaurante);
    }
}
