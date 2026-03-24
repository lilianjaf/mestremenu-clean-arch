package com.github.lilianjaf.mestremenuclean.cardapio.core.domain;

import java.util.UUID;

public class Usuario {

    private final UUID id;
    private final TipoNativo tipoNativo;

    public Usuario(UUID id, TipoNativo tipoNativo) {
        this.id = id;
        this.tipoNativo = tipoNativo;
    }

    public UUID getId() {
        return id;
    }

    public TipoNativo getTipoNativo() {
        return tipoNativo;
    }
}
