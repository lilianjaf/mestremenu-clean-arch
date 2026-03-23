package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;

import java.util.List;

public interface ListarRestaurantesUseCase {
    List<Restaurante> executar();
}
