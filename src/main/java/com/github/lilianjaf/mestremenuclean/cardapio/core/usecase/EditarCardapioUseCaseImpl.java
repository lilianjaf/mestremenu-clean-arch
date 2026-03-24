package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosAtualizacaoCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.CardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.RestauranteGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.ApenasDonoDoRestaurantePodeManipularCardapioRule;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.AtualizacaoCardapioContext;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.NomeCardapioNaoPodeSerDuplicadoRule;

public class EditarCardapioUseCaseImpl implements EditarCardapioUseCase {

    private final CardapioRepository cardapioRepository;
    private final RestauranteGateway restauranteGateway;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;
    private final NomeCardapioNaoPodeSerDuplicadoRule nomeDuplicadoRule;

    public EditarCardapioUseCaseImpl(CardapioRepository cardapioRepository,
                                    RestauranteGateway restauranteGateway,
                                    ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                    NomeCardapioNaoPodeSerDuplicadoRule nomeDuplicadoRule) {
        this.cardapioRepository = cardapioRepository;
        this.restauranteGateway = restauranteGateway;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
        this.nomeDuplicadoRule = nomeDuplicadoRule;
    }

    @Override
    public Cardapio executar(DadosAtualizacaoCardapio dados) {
        Cardapio cardapio = cardapioRepository.findById(dados.idCardapio())
                .orElseThrow(() -> new CardapioException("Cardápio não encontrado."));

        Restaurante restaurante = restauranteGateway.buscarPorId(cardapio.getIdRestaurante())
                .orElseThrow(() -> new CardapioException("Restaurante do cardápio não encontrado."));

        Usuario usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new CardapioException("Usuário logado não encontrado."));

        AtualizacaoCardapioContext context = new AtualizacaoCardapioContext(usuarioLogado, restaurante, cardapio, dados);

        ApenasDonoDoRestaurantePodeManipularCardapioRule.validar(context);
        nomeDuplicadoRule.validar(context);

        cardapio.atualizar(dados.nome(), null);

        return cardapioRepository.salvar(cardapio);
    }
}
