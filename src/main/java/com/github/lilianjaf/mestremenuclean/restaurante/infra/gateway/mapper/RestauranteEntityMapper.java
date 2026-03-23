package com.github.lilianjaf.mestremenuclean.restaurante.infra.gateway.mapper;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Endereco;
import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.infra.gateway.entity.EnderecoEmbeddable;
import com.github.lilianjaf.mestremenuclean.restaurante.infra.gateway.entity.RestauranteEntity;

public class RestauranteEntityMapper {

    public static Restaurante toDomain(RestauranteEntity entity) {
        if (entity == null) {
            return null;
        }

        Endereco endereco = new Endereco(
                entity.getEndereco().getLogradouro(),
                entity.getEndereco().getNumero(),
                entity.getEndereco().getComplemento(),
                entity.getEndereco().getBairro(),
                entity.getEndereco().getCidade(),
                entity.getEndereco().getCep(),
                entity.getEndereco().getUf()
        );

        return new Restaurante(
                entity.getId(),
                entity.getNome(),
                endereco,
                entity.getTipoCozinha(),
                entity.getHorarioFuncionamento(),
                entity.getIdDono()
        );
    }

    public static RestauranteEntity toEntity(Restaurante domain) {
        if (domain == null) {
            return null;
        }

        EnderecoEmbeddable enderecoEmbeddable = new EnderecoEmbeddable(
                domain.getEndereco().logradouro(),
                domain.getEndereco().numero(),
                domain.getEndereco().complemento(),
                domain.getEndereco().bairro(),
                domain.getEndereco().cidade(),
                domain.getEndereco().cep(),
                domain.getEndereco().uf()
        );

        return new RestauranteEntity(
                domain.getId(),
                domain.getNome(),
                enderecoEmbeddable,
                domain.getTipoCozinha(),
                domain.getHorarioFuncionamento(),
                domain.getIdDono()
        );
    }
}
