package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ListarRestaurantesRule;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ListarRestaurantesRuleContextDto;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioLogadoNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.ObterUsuarioLogadoGateway;

import java.util.List;

public class ListarRestaurantesUseCaseImpl implements ListarRestaurantesUseCase {

    private final RestauranteRepository restauranteRepository;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;
    private final List<ListarRestaurantesRule> permissaoRules;
    private final List<ListarRestaurantesRule> rules;

    public ListarRestaurantesUseCaseImpl(RestauranteRepository restauranteRepository,
                                         ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                         List<ListarRestaurantesRule> permissaoRules,
                                         List<ListarRestaurantesRule> rules) {
        this.restauranteRepository = restauranteRepository;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
        this.permissaoRules = permissaoRules;
        this.rules = rules;
    }

    @Override
    public List<Restaurante> executar() {
        UsuarioBase usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new UsuarioLogadoNaoEncontradoException("Usuário logado não encontrado"));

        ListarRestaurantesRuleContextDto context = new ListarRestaurantesRuleContextDto(usuarioLogado);

        permissaoRules.forEach(rule -> rule.validar(context));
        rules.forEach(rule -> rule.validar(context));

        return restauranteRepository.buscarTodos();
    }
}
