package com.github.lilianjaf.mestremenuclean.usuario.core.dto;

import java.util.UUID;

public record UsuarioOutput(
        UUID id,
        String nome,
        String email,
        String login,
        String tipoPerfil,
        Boolean ativo
) {
}