package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;

public class ApenasDonoDoRestaurantePodeManipularItemCardapioRule {

    public static void validar(CriacaoItemCardapioContext context) {
        if (!context.restaurante().getIdDono().equals(context.usuarioLogado().getId())) {
            throw new CardapioException("Apenas o dono do restaurante pode criar itens para este cardápio.");
        }
    }

    public static void validar(AtualizacaoItemCardapioContext context) {
        if (!context.restaurante().getIdDono().equals(context.usuarioLogado().getId())) {
            throw new CardapioException("Apenas o dono do restaurante pode editar itens deste cardápio.");
        }
    }

    public static void validar(ExclusaoItemCardapioContext context) {
        if (!context.restaurante().getIdDono().equals(context.usuarioLogado().getId())) {
            throw new CardapioException("Apenas o dono do restaurante pode excluir itens deste cardápio.");
        }
    }
}
