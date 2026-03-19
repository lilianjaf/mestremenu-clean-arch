package com.github.lilianjaf.mestremenuclean.usuario.infra.gateway.mapper;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.*;
import com.github.lilianjaf.mestremenuclean.usuario.infra.gateway.entity.EnderecoEmbeddable;
import com.github.lilianjaf.mestremenuclean.usuario.infra.gateway.entity.TipoUsuarioEntity;
import com.github.lilianjaf.mestremenuclean.usuario.infra.gateway.entity.UsuarioEntity;

import java.util.ArrayList;

public class UsuarioEntityMapper {

    public static UsuarioEntity toEntity(UsuarioBase domain) {
        if (domain == null) return null;

        EnderecoEmbeddable enderecoEntity = new EnderecoEmbeddable(
                domain.getEndereco().logradouro(),
                domain.getEndereco().numero(),
                domain.getEndereco().complemento(),
                domain.getEndereco().bairro(),
                domain.getEndereco().cidade(),
                domain.getEndereco().cep(),
                domain.getEndereco().uf()
        );

        TipoUsuarioEntity tipoEntity = new TipoUsuarioEntity(
                domain.getTipoCustomizado().getId(),
                domain.getTipoCustomizado().getNome(),
                domain.getTipoCustomizado().getTipoNativo()
        );

        return new UsuarioEntity(
                domain.getId(),
                domain.getNome(),
                domain.getEmail(),
                domain.getLogin(),
                domain.getSenha(),
                tipoEntity,
                enderecoEntity,
                domain.getDataUltimaAlteracao(),
                domain.getAtivo()
        );
    }

    public static UsuarioBase toDomain(UsuarioEntity entity) {
        if (entity == null) return null;

        Endereco enderecoDomain = new Endereco(
                entity.getEndereco().getLogradouro(),
                entity.getEndereco().getNumero(),
                entity.getEndereco().getComplemento(),
                entity.getEndereco().getBairro(),
                entity.getEndereco().getCidade(),
                entity.getEndereco().getCep(),
                entity.getEndereco().getUf()
        );

        TipoUsuario tipoDomain = new TipoUsuario(
                entity.getTipoCustomizado().getId(),
                entity.getTipoCustomizado().getNome(),
                entity.getTipoCustomizado().getTipoNativo()
        );

        if (tipoDomain.getTipoNativo() == TipoNativo.DONO) {
            return new Dono(
                    entity.getId(),
                    entity.getNome(),
                    entity.getEmail(),
                    entity.getLogin(),
                    entity.getSenha(),
                    tipoDomain,
                    enderecoDomain,
                    entity.getDataUltimaAlteracao(),
                    entity.getAtivo(),
                    new ArrayList<>()
            );
        }

        return new Cliente(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getSenha(),
                tipoDomain,
                enderecoDomain,
                entity.getDataUltimaAlteracao(),
                entity.getAtivo()
        );
    }
}