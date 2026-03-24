package com.github.lilianjaf.mestremenuclean.restaurante.core.exception;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;

public class EdicaoRestauranteNaoAutorizadaException extends DomainException {
    public EdicaoRestauranteNaoAutorizadaException(String mensagem) {
        super(mensagem);
    }
}
