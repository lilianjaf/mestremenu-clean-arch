package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

public interface ValidadorCriacaoRestauranteRule {
    <T extends CriacaoRestauranteContext> void validar(T context);
}
