package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

public interface ValidadorCardapioRule<T> {
    void validar(T context);
}
