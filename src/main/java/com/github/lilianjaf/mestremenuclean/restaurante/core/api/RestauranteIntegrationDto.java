package com.github.lilianjaf.mestremenuclean.restaurante.core.api;

import java.util.UUID;

public record RestauranteIntegrationDto(UUID id, UUID idDono, boolean ativo) {}
