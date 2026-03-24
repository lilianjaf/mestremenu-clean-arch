package com.github.lilianjaf.mestremenuclean.restaurante.core.exception;

import com.github.lilianjaf.mestremenuclean.restaurante.core.exception.DomainException;

public class RestauranteJaInativoException extends DomainException {
    public RestauranteJaInativoException(String message) {
        super(message);
    }
}
