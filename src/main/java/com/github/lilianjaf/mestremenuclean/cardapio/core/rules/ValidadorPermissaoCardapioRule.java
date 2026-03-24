package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

public interface ValidadorPermissaoCardapioRule<T> {
    void validar(T context);
}
