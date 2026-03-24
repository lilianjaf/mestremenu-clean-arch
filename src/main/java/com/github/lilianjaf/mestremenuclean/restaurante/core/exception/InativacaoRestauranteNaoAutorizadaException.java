package com.github.lilianjaf.mestremenuclean.restaurante.core.exception;

import com.github.lilianjaf.mestremenuclean.restaurante.core.exception.DomainException;

public class InativacaoRestauranteNaoAutorizadaException extends DomainException {
    public InativacaoRestauranteNaoAutorizadaException(String message) {
        super(message);
    }
}
