package com.github.lilianjaf.mestremenuclean.restaurante.infra.controller;

import java.util.UUID;

public record CriarRestauranteJson(
        String nome,
        EnderecoJson endereco,
        String tipoCozinha,
        String horarioFuncionamento,
        UUID idDono
) {
}
