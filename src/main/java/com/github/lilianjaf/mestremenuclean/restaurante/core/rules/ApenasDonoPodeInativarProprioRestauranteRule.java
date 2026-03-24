package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.exception.InativacaoRestauranteNaoAutorizadaException;

public class ApenasDonoPodeInativarProprioRestauranteRule implements InativarRestauranteRule {
    @Override
    public void validar(InativacaoRestauranteContext context) {
        if (!context.isUsuarioDonoDoRestaurante()) {
            throw new InativacaoRestauranteNaoAutorizadaException("Apenas o proprietário pode inativar o restaurante.");
        }
    }
}
