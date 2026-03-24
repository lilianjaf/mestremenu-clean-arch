package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosAtualizacaoItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.CardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ItemCardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.RestauranteGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.ApenasDonoDoRestaurantePodeManipularItemCardapioRule;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.AtualizacaoItemCardapioContext;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.NomeItemCardapioNaoPodeSerDuplicadoNoCardapioRule;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.PrecoItemCardapioDeveSerPositivoRule;

import java.util.UUID;

public class EditarItemCardapioUseCaseImpl implements EditarItemCardapioUseCase {

    private final ItemCardapioRepository itemCardapioRepository;
    private final CardapioRepository cardapioRepository;
    private final RestauranteGateway restauranteGateway;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;
    private final NomeItemCardapioNaoPodeSerDuplicadoNoCardapioRule nomeDuplicadoRule;

    public EditarItemCardapioUseCaseImpl(ItemCardapioRepository itemCardapioRepository,
                                        CardapioRepository cardapioRepository,
                                        RestauranteGateway restauranteGateway,
                                        ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                        NomeItemCardapioNaoPodeSerDuplicadoNoCardapioRule nomeDuplicadoRule) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.cardapioRepository = cardapioRepository;
        this.restauranteGateway = restauranteGateway;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
        this.nomeDuplicadoRule = nomeDuplicadoRule;
    }

    @Override
    public ItemCardapio executar(DadosAtualizacaoItemCardapio dados) {
        ItemCardapio item = itemCardapioRepository.findById(dados.idItemCardapio())
                .orElseThrow(() -> new CardapioException("Item do cardápio não encontrado."));

        var cardapio = cardapioRepository.findById(item.getIdCardapio())
                .orElseThrow(() -> new CardapioException("Cardápio associado não encontrado."));

        Restaurante restaurante = restauranteGateway.buscarPorId(cardapio.getIdRestaurante())
                .orElseThrow(() -> new CardapioException("Restaurante não encontrado."));

        Usuario usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new CardapioException("Usuário logado não encontrado."));

        AtualizacaoItemCardapioContext context = new AtualizacaoItemCardapioContext(usuarioLogado, restaurante, item, dados);

        ApenasDonoDoRestaurantePodeManipularItemCardapioRule.validar(context);
        PrecoItemCardapioDeveSerPositivoRule.validar(dados.preco());
        nomeDuplicadoRule.validar(context);

        item.atualizar(
                dados.nome(),
                dados.descricao(),
                dados.preco(),
                dados.disponibilidadeRestaurante(),
                dados.caminhoFoto()
        );

        return itemCardapioRepository.salvar(item);
    }
}
