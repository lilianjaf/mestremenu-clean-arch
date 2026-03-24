package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ItemCardapioRepository;

public class NomeItemCardapioNaoPodeSerDuplicadoNoCardapioRule implements ValidadorItemCardapioRule<CriacaoItemCardapioContext> {

    private final ItemCardapioRepository itemCardapioRepository;

    public NomeItemCardapioNaoPodeSerDuplicadoNoCardapioRule(ItemCardapioRepository itemCardapioRepository) {
        this.itemCardapioRepository = itemCardapioRepository;
    }

    @Override
    public void validar(CriacaoItemCardapioContext context) {
        if (itemCardapioRepository.existeNomeNoCardapio(context.dados().nome(), context.dados().idCardapio())) {
            throw new CardapioException("Já existe um item com este nome neste cardápio.");
        }
    }

    public void validar(AtualizacaoItemCardapioContext context) {
        if (!context.itemExistente().getNome().equalsIgnoreCase(context.dados().nome()) &&
            itemCardapioRepository.existeNomeNoCardapio(context.dados().nome(), context.itemExistente().getIdCardapio())) {
            throw new CardapioException("Já existe um item com este nome neste cardápio.");
        }
    }
}
