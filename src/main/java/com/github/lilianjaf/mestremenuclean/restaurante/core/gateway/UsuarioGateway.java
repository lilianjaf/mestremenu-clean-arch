package com.github.lilianjaf.mestremenuclean.restaurante.core.gateway;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioGateway {
    Optional<Usuario> buscarPorId(UUID id);
}
