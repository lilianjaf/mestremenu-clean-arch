package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.ItemCardapioRuleContext;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CriacaoItemNaoAutorizadaException;

public class ApenasDonoPodeCriarItemParaProprioRestauranteRule implements ValidadorPermissaoItemCardapioRule<ItemCardapioRuleContext> {

    @Override
    public void validar(ItemCardapioRuleContext context) {
        if (!context.isUsuarioDonoDoRestauranteDoItem()) {
            throw new CriacaoItemNaoAutorizadaException("Apenas o dono pode criar itens para seu restaurante.");
        }
    }
}
