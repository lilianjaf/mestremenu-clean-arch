package com.github.lilianjaf.mestremenuclean.restaurante.infra.controller;

import java.util.UUID;

public record RestauranteResponseJson(
        UUID id,
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        UUID idDono
) {
}
