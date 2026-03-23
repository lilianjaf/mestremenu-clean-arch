package com.github.lilianjaf.mestremenuclean.restaurante.core.dto;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Endereco;

public record DadosAtualizacaoRestaurante(
        String nome,
        Endereco endereco,
        String tipoCozinha,
        String horarioFuncionamento
) {
}
