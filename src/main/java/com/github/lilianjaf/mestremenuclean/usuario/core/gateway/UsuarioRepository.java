package com.github.lilianjaf.mestremenuclean.usuario.core.gateway;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    UsuarioBase salvar(UsuarioBase usuario);
    Optional<UsuarioBase> findByLogin(String login);
    Optional<UsuarioBase> findById(UUID id);
    Optional<UsuarioBase> findByEmail(String email);
    boolean existeUsuarioComTipo(UUID idTipoUsuario);
}