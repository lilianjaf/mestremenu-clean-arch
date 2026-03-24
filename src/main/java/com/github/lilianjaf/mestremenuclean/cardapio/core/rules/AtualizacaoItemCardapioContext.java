package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosAtualizacaoItemCardapio;

public record AtualizacaoItemCardapioContext(
        Usuario usuarioLogado,
        Restaurante restaurante,
        ItemCardapio itemExistente,
        DadosAtualizacaoItemCardapio dados
) {
}
