package com.github.lilianjaf.mestremenuclean.restaurante.infra.gateway;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.infra.gateway.entity.RestauranteEntity;
import com.github.lilianjaf.mestremenuclean.restaurante.infra.gateway.mapper.RestauranteEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RestauranteRepositoryJpaImpl implements RestauranteRepository {

    private final SpringDataRestauranteRepository springDataRepository;

    public RestauranteRepositoryJpaImpl(SpringDataRestauranteRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        RestauranteEntity entity = RestauranteEntityMapper.toEntity(restaurante);
        RestauranteEntity savedEntity = springDataRepository.save(entity);
        return RestauranteEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Restaurante> findById(UUID id) {
        return springDataRepository.findById(id).map(RestauranteEntityMapper::toDomain);
    }

    @Override
    public List<Restaurante> buscarTodos() {
        return springDataRepository.findAll().stream()
                .map(RestauranteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(UUID id) {
        springDataRepository.deleteById(id);
    }
}
