package com.github.lilianjaf.mestremenuclean.restaurante.infra.config;

import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.*;
import com.github.lilianjaf.mestremenuclean.restaurante.core.usecase.*;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.ObterUsuarioLogadoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RestauranteConfig {

    @Bean
    public CriarRestauranteUseCase criarRestauranteUseCase(RestauranteRepository restauranteRepository, UsuarioGateway usuarioGateway) {
        List<ValidadorCriacaoRestauranteRule> regras = List.of(
                new ApenasDonoPodeCriarRestauranteRule()
        );
        return new CriarRestauranteUseCaseImpl(restauranteRepository, usuarioGateway, regras);
    }

    @Bean
    public BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase(RestauranteRepository restauranteRepository) {
        return new BuscarRestaurantePorIdUseCaseImpl(restauranteRepository);
    }

    @Bean
    public ListarRestaurantesUseCase listarRestaurantesUseCase(RestauranteRepository restauranteRepository) {
        return new ListarRestaurantesUseCaseImpl(restauranteRepository);
    }

    @Bean
    public AtualizarRestauranteUseCase atualizarRestauranteUseCase(RestauranteRepository restauranteRepository,
                                                                 ObterUsuarioLogadoGateway obterUsuarioLogadoGateway) {
        List<AtualizarRestauranteRule> permissaoRules = List.of(
                new ApenasDonoDoRestaurantePodeAtualizarRule()
        );
        List<AtualizarRestauranteRule> rules = List.of(
                new RestauranteDeveTerDonoVinculadoRule()
        );
        return new AtualizarRestauranteUseCaseImpl(restauranteRepository, obterUsuarioLogadoGateway, permissaoRules, rules);
    }

    @Bean
    public ExcluirRestauranteUseCase excluirRestauranteUseCase(RestauranteRepository restauranteRepository) {
        return new ExcluirRestauranteUseCaseImpl(restauranteRepository);
    }
}
