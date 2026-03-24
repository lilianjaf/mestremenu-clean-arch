package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.CardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ItemCardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.RestauranteGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.ApenasDonoDoRestaurantePodeManipularItemCardapioRule;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.ExclusaoItemCardapioContext;

import java.util.UUID;

public class DeletarItemCardapioUseCaseImpl implements DeletarItemCardapioUseCase {

    private final ItemCardapioRepository itemCardapioRepository;
    private final CardapioRepository cardapioRepository;
    private final RestauranteGateway restauranteGateway;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;

    public DeletarItemCardapioUseCaseImpl(ItemCardapioRepository itemCardapioRepository,
                                         CardapioRepository cardapioRepository,
                                         RestauranteGateway restauranteGateway,
                                         ObterUsuarioLogadoGateway obterUsuarioLogadoGateway) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.cardapioRepository = cardapioRepository;
        this.restauranteGateway = restauranteGateway;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
    }

    @Override
    public void executar(UUID idItem) {
        ItemCardapio item = itemCardapioRepository.findById(idItem)
                .orElseThrow(() -> new CardapioException("Item do cardápio não encontrado para exclusão."));

        var cardapio = cardapioRepository.findById(item.getIdCardapio())
                .orElseThrow(() -> new CardapioException("Cardápio associado não encontrado."));

        Restaurante restaurante = restauranteGateway.buscarPorId(cardapio.getIdRestaurante())
                .orElseThrow(() -> new CardapioException("Restaurante não encontrado."));

        Usuario usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new CardapioException("Usuário logado não encontrado."));

        ExclusaoItemCardapioContext context = new ExclusaoItemCardapioContext(usuarioLogado, restaurante, item);

        ApenasDonoDoRestaurantePodeManipularItemCardapioRule.validar(context);

        itemCardapioRepository.deletar(idItem);
    }
}
