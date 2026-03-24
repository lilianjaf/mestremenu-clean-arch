package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosCriacaoItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.CardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ItemCardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.RestauranteGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.*;

import java.util.List;

public class CriarItemCardapioUseCaseImpl implements CriarItemCardapioUseCase {

    private final ItemCardapioRepository itemCardapioRepository;
    private final CardapioRepository cardapioRepository;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;
    private final RestauranteGateway restauranteGateway;
    private final List<ValidadorPermissaoItemCardapioRule<CriacaoItemCardapioContext>> permissionRules;
    private final List<ValidadorItemCardapioRule<CriacaoItemCardapioContext>> businessRules;

    public CriarItemCardapioUseCaseImpl(ItemCardapioRepository itemCardapioRepository,
                                       CardapioRepository cardapioRepository,
                                       ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                       RestauranteGateway restauranteGateway,
                                       List<ValidadorPermissaoItemCardapioRule<CriacaoItemCardapioContext>> permissionRules,
                                       List<ValidadorItemCardapioRule<CriacaoItemCardapioContext>> businessRules) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.cardapioRepository = cardapioRepository;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
        this.restauranteGateway = restauranteGateway;
        this.permissionRules = permissionRules;
        this.businessRules = businessRules;
    }

    @Override
    public ItemCardapio executar(DadosCriacaoItemCardapio dados) {
        Usuario usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new CardapioException("Usuário logado não encontrado."));

        var cardapio = cardapioRepository.findById(dados.idCardapio())
                .orElseThrow(() -> new CardapioException("Cardápio não encontrado."));

        Restaurante restaurante = restauranteGateway.buscarPorId(cardapio.getIdRestaurante())
                .orElseThrow(() -> new CardapioException("Restaurante do cardápio não encontrado."));

        CriacaoItemCardapioContext context = new CriacaoItemCardapioContext(usuarioLogado, restaurante, dados);

        // Validation chain
        permissionRules.forEach(r -> r.validar(context));
        ApenasDonoDoRestaurantePodeManipularItemCardapioRule.validar(context);
        PrecoItemCardapioDeveSerPositivoRule.validar(dados.preco());
        businessRules.forEach(r -> r.validar(context));

        ItemCardapio item = new ItemCardapio(
                dados.nome(),
                dados.descricao(),
                dados.preco(),
                dados.disponibilidadeRestaurante(),
                dados.caminhoFoto(),
                dados.idCardapio()
        );

        return itemCardapioRepository.salvar(item);
    }
}
