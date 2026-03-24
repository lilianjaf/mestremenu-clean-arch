package com.github.lilianjaf.mestremenuclean.restaurante.core.exception;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;

public class CriacaoRestauranteNaoAutorizadaException extends DomainException {
    public CriacaoRestauranteNaoAutorizadaException(String mensagem) {
        super(mensagem);
    }
}
