package com.github.lilianjaf.mestremenuclean.cardapio.infra.controller;

import java.util.UUID;

public record CardapioResponseJson(
        UUID id,
        String nome,
        UUID idRestaurante
) {
}
