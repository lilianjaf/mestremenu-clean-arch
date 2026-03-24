package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;

public class ApenasDonoDoRestaurantePodeManipularCardapioRule {

    public static void validar(CriacaoCardapioContext context) {
        if (!context.restaurante().getIdDono().equals(context.usuarioLogado().getId())) {
            throw new CardapioException("Apenas o dono do restaurante pode criar cardápios para ele.");
        }
    }

    public static void validar(AtualizacaoCardapioContext context) {
        if (!context.restaurante().getIdDono().equals(context.usuarioLogado().getId())) {
            throw new CardapioException("Apenas o dono do restaurante pode editar este cardápio.");
        }
    }

    public static void validar(ExclusaoCardapioContext context) {
        if (!context.restaurante().getIdDono().equals(context.usuarioLogado().getId())) {
            throw new CardapioException("Apenas o dono do restaurante pode excluir este cardápio.");
        }
    }
}
