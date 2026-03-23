package com.github.lilianjaf.mestremenuclean.restaurante.infra.gateway;

import com.github.lilianjaf.mestremenuclean.restaurante.infra.gateway.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataRestauranteRepository extends JpaRepository<RestauranteEntity, UUID> {
}
