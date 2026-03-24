package com.github.lilianjaf.mestremenuclean.cardapio.core.dto;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.TipoNativo;

public record DeletarItemCardapioRuleContextDto(
    Usuario usuarioLogado,
    Restaurante restaurante,
    ItemCardapio item,
    boolean isItemDoProprioRestaurante
) {
    public boolean isUsuarioTipoDono() {
        return usuarioLogado != null && usuarioLogado.getTipoNativo() == TipoNativo.DONO;
    }

    public boolean isItemDoProprioRestaurante() {
        return isItemDoProprioRestaurante;
    }
}
