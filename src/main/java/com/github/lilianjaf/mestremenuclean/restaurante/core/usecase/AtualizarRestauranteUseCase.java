package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.dto.DadosAtualizacaoRestaurante;
import java.util.UUID;

public interface AtualizarRestauranteUseCase {
    Restaurante executar(UUID id, DadosAtualizacaoRestaurante dados);
}
