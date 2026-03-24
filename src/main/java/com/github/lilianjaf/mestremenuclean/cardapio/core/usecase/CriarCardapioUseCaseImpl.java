package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosCriacaoCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.CardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.RestauranteGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.*;

import java.util.List;
import java.util.stream.Collectors;

public class CriarCardapioUseCaseImpl implements CriarCardapioUseCase {

        private final CardapioRepository cardapioRepository;
        private final RestauranteGateway restauranteGateway;
        private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;
        private final List<ValidadorPermissaoCardapioRule<CriacaoCardapioContext>> permissionRules;
        private final List<ValidadorCardapioRule<CriacaoCardapioContext>> businessRules;

        public CriarCardapioUseCaseImpl(CardapioRepository cardapioRepository,
                        RestauranteGateway restauranteGateway,
                        ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                        List<ValidadorPermissaoCardapioRule<CriacaoCardapioContext>> permissionRules,
                        List<ValidadorCardapioRule<CriacaoCardapioContext>> businessRules) {
                this.cardapioRepository = cardapioRepository;
                this.restauranteGateway = restauranteGateway;
                this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
                this.permissionRules = permissionRules;
                this.businessRules = businessRules;
        }

        @Override
        public Cardapio executar(DadosCriacaoCardapio dados) {
                Usuario usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                                .orElseThrow(() -> new CardapioException("Usuário logado não encontrado."));

                Restaurante restaurante = restauranteGateway.buscarPorId(dados.idRestaurante())
                                .orElseThrow(() -> new CardapioException("Restaurante não encontrado."));

                CriacaoCardapioContext context = new CriacaoCardapioContext(usuarioLogado, restaurante, dados);

                permissionRules.forEach(r -> r.validar(context));
                ApenasDonoDoRestaurantePodeManipularCardapioRule.validar(context);
                CardapioDeveConterPeloMenosUmItemRule.validar(context);
                businessRules.forEach(r -> r.validar(context));

                Cardapio cardapio = new Cardapio(dados.nome(), dados.idRestaurante(), null);

                List<ItemCardapio> itensToCreate = dados.itens().stream()
                                .map(itemDados -> {
                                        PrecoItemCardapioDeveSerPositivoRule.validar(itemDados.preco());
                                        return new ItemCardapio(
                                                        itemDados.nome(),
                                                        itemDados.descricao(),
                                                        itemDados.preco(),
                                                        itemDados.disponibilidadeRestaurante(),
                                                        itemDados.caminhoFoto(),
                                                        cardapio.getId());
                                }).collect(Collectors.toList());

                cardapio.atualizar(cardapio.getNome(), itensToCreate);

                return cardapioRepository.salvar(cardapio);
        }
}
