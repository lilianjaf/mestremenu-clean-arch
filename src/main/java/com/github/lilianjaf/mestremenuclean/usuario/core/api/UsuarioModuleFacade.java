package com.github.lilianjaf.mestremenuclean.usuario.core.api;

import java.util.UUID;

public interface UsuarioModuleFacade {
    UsuarioIntegrationDto buscarUsuarioParaIntegracao(UUID id);
    UsuarioIntegrationDto buscarPorUsuario(String username);
}