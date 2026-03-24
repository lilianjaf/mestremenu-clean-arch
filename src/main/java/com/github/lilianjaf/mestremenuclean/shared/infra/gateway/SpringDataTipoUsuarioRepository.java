package com.github.lilianjaf.mestremenuclean.shared.infra.gateway;

import com.github.lilianjaf.mestremenuclean.shared.infra.gateway.entity.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataTipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, UUID> {
    Optional<TipoUsuarioEntity> findByNome(String nome);
}