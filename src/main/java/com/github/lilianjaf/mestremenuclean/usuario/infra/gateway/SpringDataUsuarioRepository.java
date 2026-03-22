package com.github.lilianjaf.mestremenuclean.usuario.infra.gateway;

import com.github.lilianjaf.mestremenuclean.usuario.infra.gateway.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findById(UUID id);
    Optional<UsuarioEntity> findByLogin(String login);
    boolean existsByTipoCustomizadoId(UUID idTipoUsuario);
}