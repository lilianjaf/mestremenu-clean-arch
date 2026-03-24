package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.ItemCardapioRuleContext;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.AlteracaoItemNaoAutorizadaException;

public class ApenasDonoDoRestaurantePodeAlterarItemRule implements ValidadorPermissaoItemCardapioRule<ItemCardapioRuleContext> {
    @Override
    public void validar(ItemCardapioRuleContext context) {
        if (!context.isUsuarioDonoDoRestauranteDoItem()) {
            throw new AlteracaoItemNaoAutorizadaException("Apenas o dono do restaurante pode alterar itens do cardápio.");
        }
    }
}
