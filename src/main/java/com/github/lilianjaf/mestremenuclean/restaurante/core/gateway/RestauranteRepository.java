package com.github.lilianjaf.mestremenuclean.restaurante.core.gateway;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface RestauranteRepository {
    Restaurante salvar(Restaurante restaurante);
    Optional<Restaurante> findById(UUID id);
    List<Restaurante> buscarTodos();
    void deletar(UUID id);
}
