package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;

import java.math.BigDecimal;

public class PrecoItemCardapioDeveSerPositivoRule {

    public static void validar(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CardapioException("O preço do item deve ser maior que zero.");
        }
    }
}
