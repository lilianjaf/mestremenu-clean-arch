package com.github.lilianjaf.mestremenuclean.restaurante.infra.config;

import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.*;
import com.github.lilianjaf.mestremenuclean.restaurante.core.usecase.*;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.UsuarioDeveEstarAutenticadoRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RestauranteConfig {

    @Bean
    public CriarRestauranteUseCase criarRestauranteUseCase(RestauranteRepository restauranteRepository,
                                                           UsuarioGateway usuarioGateway,
                                                           ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                                           TransactionGateway transactionGateway) {
        List<ValidadorCriacaoRestauranteRule> permissaoRules = List.of(
                new ApenasDonoPodeCriarRestauranteRule()
        );
        List<ValidadorCriacaoRestauranteRule> rules = List.of(
                new RestauranteDeveTerDonoVinculadoRule()
        );
        return new CriarRestauranteUseCaseImpl(restauranteRepository, usuarioGateway, obterUsuarioLogadoGateway, transactionGateway, permissaoRules, rules);
    }

    @Bean
    public BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase(RestauranteRepository restauranteRepository,
                                                                     ObterUsuarioLogadoGateway obterUsuarioLogadoGateway) {
        List<BuscarRestauranteRule> permissaoRules = List.of(
                new UsuarioDeveEstarAutenticadoRule()
        );
        List<BuscarRestauranteRule> rules = List.of();
        return new BuscarRestaurantePorIdUseCaseImpl(restauranteRepository, obterUsuarioLogadoGateway, permissaoRules, rules);
    }

    @Bean
    public ListarRestaurantesUseCase listarRestaurantesUseCase(RestauranteRepository restauranteRepository,
                                                             ObterUsuarioLogadoGateway obterUsuarioLogadoGateway) {
        List<ListarRestaurantesRule> permissaoRules = List.of(
                new UsuarioDeveEstarAutenticadoRule()
        );
        List<ListarRestaurantesRule> rules = List.of();
        return new ListarRestaurantesUseCaseImpl(restauranteRepository, obterUsuarioLogadoGateway, permissaoRules, rules);
    }

    @Bean
    public AtualizarRestauranteUseCase atualizarRestauranteUseCase(RestauranteRepository restauranteRepository,
                                                                 ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                                                 TransactionGateway transactionGateway) {
        List<AtualizarRestauranteRule> permissaoRules = List.of(
                new ApenasDonoDoRestaurantePodeAtualizarRule()
        );
        List<AtualizarRestauranteRule> rules = List.of(
                new RestauranteDeveTerDonoVinculadoRule()
        );
        return new AtualizarRestauranteUseCaseImpl(restauranteRepository, obterUsuarioLogadoGateway, transactionGateway, permissaoRules, rules);
    }

    @Bean
    public InativarRestauranteUseCase inativarRestauranteUseCase(RestauranteRepository restauranteRepository,
                                                                 ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                                                 TransactionGateway transactionGateway) {
        List<InativarRestauranteRule> permissaoRules = List.of(
                new ApenasDonoPodeInativarProprioRestauranteRule()
        );
        List<InativarRestauranteRule> rules = List.of(
                new RestauranteDeveEstarAtivoRule()
        );
        return new InativarRestauranteUseCaseImpl(restauranteRepository, obterUsuarioLogadoGateway, transactionGateway, permissaoRules, rules);
    }
}
