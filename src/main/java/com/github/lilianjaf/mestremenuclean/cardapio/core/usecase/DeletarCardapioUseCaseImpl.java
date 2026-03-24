package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.CardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.RestauranteGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.ApenasDonoDoRestaurantePodeManipularCardapioRule;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.ExclusaoCardapioContext;

import java.util.UUID;

public class DeletarCardapioUseCaseImpl implements DeletarCardapioUseCase {

    private final CardapioRepository cardapioRepository;
    private final RestauranteGateway restauranteGateway;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;

    public DeletarCardapioUseCaseImpl(CardapioRepository cardapioRepository,
                                     RestauranteGateway restauranteGateway,
                                     ObterUsuarioLogadoGateway obterUsuarioLogadoGateway) {
        this.cardapioRepository = cardapioRepository;
        this.restauranteGateway = restauranteGateway;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
    }

    @Override
    public void executar(UUID idCardapio) {
        Cardapio cardapio = cardapioRepository.findById(idCardapio)
                .orElseThrow(() -> new CardapioException("Cardápio não encontrado para exclusão."));

        Restaurante restaurante = restauranteGateway.buscarPorId(cardapio.getIdRestaurante())
                .orElseThrow(() -> new CardapioException("Restaurante do cardápio não encontrado."));

        Usuario usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new CardapioException("Usuário logado não encontrado."));

        ExclusaoCardapioContext context = new ExclusaoCardapioContext(usuarioLogado, restaurante, cardapio);

        ApenasDonoDoRestaurantePodeManipularCardapioRule.validar(context);

        cardapioRepository.deletar(idCardapio);
    }
}
