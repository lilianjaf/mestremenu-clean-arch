package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.BuscarCardapioPorRestauranteRuleContextDto;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.UsuarioLogadoNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.CardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.ValidadorPermissaoCardapioRule;

import java.util.List;
import java.util.UUID;

public class BuscarCardapioPorRestauranteUseCaseImpl implements BuscarCardapioPorRestauranteUseCase {

    private final CardapioRepository cardapioRepository;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;
    private final List<ValidadorPermissaoCardapioRule<BuscarCardapioPorRestauranteRuleContextDto>> permissaoRules;

    public BuscarCardapioPorRestauranteUseCaseImpl(CardapioRepository cardapioRepository,
                                                  ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                                  List<ValidadorPermissaoCardapioRule<BuscarCardapioPorRestauranteRuleContextDto>> permissaoRules) {
        this.cardapioRepository = cardapioRepository;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
        this.permissaoRules = permissaoRules;
    }

    @Override
    public List<Cardapio> executar(UUID idRestaurante) {
        Usuario usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new UsuarioLogadoNaoEncontradoException("Usuário logado não encontrado"));

        BuscarCardapioPorRestauranteRuleContextDto context = new BuscarCardapioPorRestauranteRuleContextDto(usuarioLogado);

        permissaoRules.forEach(rule -> rule.validar(context));

        return cardapioRepository.buscarPorIdRestaurante(idRestaurante);
    }
}
