package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

public interface ValidadorItemCardapioRule<T> {
    void validar(T context);
}
