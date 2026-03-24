package com.github.lilianjaf.mestremenuclean.restaurante.core.exception;

public class DomainException extends RuntimeException {
    public DomainException(String mensagem) {
        super(mensagem);
    }
}