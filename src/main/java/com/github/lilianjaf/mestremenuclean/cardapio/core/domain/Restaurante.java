package com.github.lilianjaf.mestremenuclean.cardapio.core.domain;

import java.util.UUID;

public class Restaurante {

    private final UUID id;
    private final UUID idDono;

    public Restaurante(UUID id, UUID idDono) {
        this.id = id;
        this.idDono = idDono;
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdDono() {
        return idDono;
    }
}
