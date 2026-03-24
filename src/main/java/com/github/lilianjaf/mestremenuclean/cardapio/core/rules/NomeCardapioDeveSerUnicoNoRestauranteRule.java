package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.AlterarCardapioRuleContextDto;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.NomeCardapioJaEmUsoException;

public class NomeCardapioDeveSerUnicoNoRestauranteRule implements ValidadorCardapioRule<AlterarCardapioRuleContextDto> {
    @Override
    public void validar(AlterarCardapioRuleContextDto context) {
        if (!context.isNomeUnico()) {
            throw new NomeCardapioJaEmUsoException("Já existe um cardápio com este nome para este restaurante.");
        }
    }
}
