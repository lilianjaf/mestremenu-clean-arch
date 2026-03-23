package com.github.lilianjaf.mestremenuclean.restaurante.core.dto;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Endereco;
import java.util.UUID;

public record DadosCriacaoRestaurante(
        String nome,
        Endereco endereco,
        String tipoCozinha,
        String horarioFuncionamento,
        UUID idDono
) {
}
