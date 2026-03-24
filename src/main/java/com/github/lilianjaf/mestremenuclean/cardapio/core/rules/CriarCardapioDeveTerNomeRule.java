package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.AlterarCardapioRuleContextDto;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.CriarCardapioRuleContextDto;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.NomeCardapioObrigatorioException;

public class CriarCardapioDeveTerNomeRule implements ValidadorCardapioRule<CriarCardapioRuleContextDto> {
    
    @Override
    public void validar(CriarCardapioRuleContextDto context) {
        if (!context.hasNome()) {
            throw new NomeCardapioObrigatorioException("O cardápio precisa ter um nome preenchido.");
        }
    }
}
