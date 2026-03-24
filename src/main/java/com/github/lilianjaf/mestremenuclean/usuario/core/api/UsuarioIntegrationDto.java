package com.github.lilianjaf.mestremenuclean.usuario.core.api;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;

import java.util.UUID;

public record UsuarioIntegrationDto(UUID id, String nomeDoTipo, String tipoNativo, boolean ativo) {}