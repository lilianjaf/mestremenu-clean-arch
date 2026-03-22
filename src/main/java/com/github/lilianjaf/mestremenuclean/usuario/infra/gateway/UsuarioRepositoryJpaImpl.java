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
        UsuarioEntity entity;

        if (usuarioDomain.getId() != null && jpaRepository.existsById(usuarioDomain.getId())) {
            entity = jpaRepository.findById(usuarioDomain.getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado para atualização"));

            UsuarioEntityMapper.atualizarEntity(usuarioDomain, entity);

            UsuarioEntity entitySalva = jpaRepository.save(entity);
            return UsuarioEntityMapper.toDomain(entitySalva);

        } else {
            entity = UsuarioEntityMapper.toEntity(usuarioDomain);
            jpaRepository.save(entity);

            return usuarioDomain;
        }
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