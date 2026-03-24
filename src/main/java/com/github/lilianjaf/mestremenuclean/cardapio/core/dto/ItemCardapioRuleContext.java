package com.github.lilianjaf.mestremenuclean.cardapio.core.dto;

public interface ItemCardapioRuleContext {
    boolean isUsuarioDonoDoRestauranteDoItem();
    boolean hasRestauranteVinculado();
    boolean hasTodosCamposPreenchidos();
    boolean isNomeUnico();
    boolean isPrecoValido();
}
