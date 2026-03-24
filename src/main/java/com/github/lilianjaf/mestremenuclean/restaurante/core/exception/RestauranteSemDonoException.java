package com.github.lilianjaf.mestremenuclean.restaurante.core.exception;

import com.github.lilianjaf.mestremenuclean.restaurante.core.exception.DomainException;

public class RestauranteSemDonoException extends DomainException {
    public RestauranteSemDonoException(String mensagem) {
        super(mensagem);
    }
}
