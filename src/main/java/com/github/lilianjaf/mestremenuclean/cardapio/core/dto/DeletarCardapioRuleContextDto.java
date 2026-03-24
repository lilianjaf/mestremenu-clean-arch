package com.github.lilianjaf.mestremenuclean.cardapio.core.dto;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.TipoNativo;

public record DeletarCardapioRuleContextDto(
    Usuario usuarioLogado,
    Restaurante restaurante,
    Cardapio cardapio,
    boolean isCardapioDoProprioRestaurante
) {
    public boolean isUsuarioTipoDono() {
        return usuarioLogado != null && usuarioLogado.getTipoNativo() == TipoNativo.DONO;
    }

    public boolean isCardapioDoProprioRestaurante() {
        return isCardapioDoProprioRestaurante;
    }
}
