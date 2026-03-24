package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.exception.RestauranteSemDonoException;

public class RestauranteDeveTerDonoVinculadoRule implements AtualizarRestauranteRule {
    @Override
    public void validar(AtualizarRestauranteRuleContextDto context) {
        if (!context.restaurantePossuiDono()) {
            throw new RestauranteSemDonoException("O restaurante deve possuir um dono vinculado.");
        }
    }
}
