package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.exception.RestauranteJaInativoException;

public class RestauranteDeveEstarAtivoRule implements InativarRestauranteRule {
    @Override
    public void validar(InativacaoRestauranteContext context) {
        if (!context.isRestauranteAtivo()) {
            throw new RestauranteJaInativoException("O restaurante já se encontra inativo.");
        }
    }
}
