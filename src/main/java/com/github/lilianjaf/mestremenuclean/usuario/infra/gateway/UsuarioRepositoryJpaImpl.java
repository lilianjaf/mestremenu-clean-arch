package com.github.lilianjaf.mestremenuclean.usuario.infra.gateway;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.infra.gateway.entity.UsuarioEntity;
import com.github.lilianjaf.mestremenuclean.usuario.infra.gateway.mapper.UsuarioEntityMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UsuarioRepositoryJpaImpl implements UsuarioRepository {

    private final SpringDataUsuarioRepository jpaRepository;

    public UsuarioRepositoryJpaImpl(SpringDataUsuarioRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UsuarioBase salvar(UsuarioBase usuarioDomain) {
        UsuarioEntity entity = UsuarioEntityMapper.toEntity(usuarioDomain);
        UsuarioEntity entitySalva = jpaRepository.save(entity);
        return UsuarioEntityMapper.toDomain(entitySalva);
    }

    @Override
    public Optional<UsuarioBase> findByLogin(String login) {
        return jpaRepository.findByLogin(login)
                .map(UsuarioEntityMapper::toDomain);
    }

    @Override
    public Optional<UsuarioBase> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(UsuarioEntityMapper::toDomain);
    }

    @Override
    public boolean existeUsuarioComTipo(UUID idTipoUsuario) {
        return jpaRepository.existsByTipoCustomizadoId(idTipoUsuario);
    }
}