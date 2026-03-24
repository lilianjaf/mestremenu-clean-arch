package com.github.lilianjaf.mestremenuclean.cardapio.infra.controller;

import java.math.BigDecimal;
import java.util.UUID;

public record CriarItemCardapioJson(
        String nome,
        String descricao,
        BigDecimal preco,
        boolean disponibilidadeRestaurante,
        String caminhoFoto,
        UUID idCardapio
) {
}
