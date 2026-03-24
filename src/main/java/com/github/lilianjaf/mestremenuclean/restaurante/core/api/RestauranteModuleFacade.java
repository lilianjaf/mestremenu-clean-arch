package com.github.lilianjaf.mestremenuclean.restaurante.core.api;

import java.util.UUID;

public interface RestauranteModuleFacade {
    RestauranteIntegrationDto buscarPorId(UUID id);
}
