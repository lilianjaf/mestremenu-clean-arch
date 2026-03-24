package com.github.lilianjaf.mestremenuclean.cardapio.infra.controller;

import java.util.UUID;

public record AtualizarCardapioJson(
        String nome,
        UUID idUsuarioLogado
) {
}
