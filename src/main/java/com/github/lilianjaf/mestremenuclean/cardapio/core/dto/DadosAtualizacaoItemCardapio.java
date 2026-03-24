package com.github.lilianjaf.mestremenuclean.cardapio.core.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record DadosAtualizacaoItemCardapio(
        UUID idItemCardapio,
        String nome,
        String descricao,
        BigDecimal preco,
        boolean disponibilidadeRestaurante,
        String caminhoFoto
) {
}
