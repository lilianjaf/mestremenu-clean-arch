package com.github.lilianjaf.mestremenuclean.cardapio.core.dto;

import java.util.UUID;

public record DadosAtualizacaoCardapio(
        UUID idCardapio,
        String nome
) {
}
