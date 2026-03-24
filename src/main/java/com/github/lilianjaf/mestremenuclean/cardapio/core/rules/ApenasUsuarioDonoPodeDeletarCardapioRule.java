package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DeletarCardapioRuleContextDto;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.AcessoNegadoDelecaoCardapioException;

public class ApenasUsuarioDonoPodeDeletarCardapioRule implements ValidadorPermissaoCardapioRule<DeletarCardapioRuleContextDto> {
    @Override
    public void validar(DeletarCardapioRuleContextDto context) {
        if (!context.isUsuarioTipoDono()) {
            throw new AcessoNegadoDelecaoCardapioException("Acesso negado: apenas usuários com perfil DONO podem deletar cardápios.");
        }
    }
}
