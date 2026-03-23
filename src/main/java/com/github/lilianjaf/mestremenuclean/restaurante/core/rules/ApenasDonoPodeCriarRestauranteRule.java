package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;

public class ApenasDonoPodeCriarRestauranteRule implements ValidadorCriacaoRestauranteRule {

    @Override
    public void validar(CriacaoRestauranteContext context) {
        if (!context.dono().getTipoNativo().equals(TipoNativo.DONO)) {
            throw new DomainException("O usuário informado não possui perfil de DONO para criar um restaurante.");
        }
    }
}
