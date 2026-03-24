package com.github.lilianjaf.mestremenuclean.cardapio.core.gateway;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemCardapioRepository {
    ItemCardapio salvar(ItemCardapio item);
    Optional<ItemCardapio> findById(UUID id);
    List<ItemCardapio> buscarPorIdCardapio(UUID idCardapio);
    boolean existeNomeNoCardapio(String nome, UUID idCardapio);
    void deletar(UUID id);
}
