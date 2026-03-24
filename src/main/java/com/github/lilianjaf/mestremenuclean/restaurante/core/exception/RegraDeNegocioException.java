package com.github.lilianjaf.mestremenuclean.restaurante.core.exception;

import com.github.lilianjaf.mestremenuclean.restaurante.core.exception.DomainException;

public class RegraDeNegocioException extends DomainException {
    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}