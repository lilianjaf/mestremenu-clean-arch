package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DeletarItemCardapioRuleContextDto;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.AcessoNegadoDelecaoItemException;

public class ApenasUsuarioDonoPodeDeletarItemRule implements ValidadorPermissaoCardapioRule<DeletarItemCardapioRuleContextDto> {
    @Override
    public void validar(DeletarItemCardapioRuleContextDto context) {
        if (!context.isUsuarioTipoDono()) {
            throw new AcessoNegadoDelecaoItemException("Acesso negado: apenas usuários com perfil DONO podem deletar itens de cardápio.");
        }
    }
}
