package com.github.lilianjaf.mestremenuclean.cardapio.infra.config;

import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.*;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.*;
import com.github.lilianjaf.mestremenuclean.cardapio.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CardapioConfig {

    @Bean
    public CriarItemCardapioUseCase criarItemCardapioUseCase(ItemCardapioRepository itemRepository,
                                                           CardapioRepository cardapioRepository,
                                                           ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                                           RestauranteGateway restauranteGateway) {
        List<ValidadorPermissaoItemCardapioRule<CriacaoItemCardapioContext>> permissionRules = List.of(
                new ApenasDonoPodeCriarItemCardapioRule()
        );
        List<ValidadorItemCardapioRule<CriacaoItemCardapioContext>> businessRules = List.of(
                new NomeItemCardapioNaoPodeSerDuplicadoNoCardapioRule(itemRepository)
        );
        return new CriarItemCardapioUseCaseImpl(itemRepository, cardapioRepository, obterUsuarioLogadoGateway, restauranteGateway, permissionRules, businessRules);
    }

    @Bean
    public EditarItemCardapioUseCase editarItemCardapioUseCase(ItemCardapioRepository itemRepository,
                                                             CardapioRepository cardapioRepository,
                                                             RestauranteGateway restauranteGateway,
                                                             ObterUsuarioLogadoGateway obterUsuarioLogadoGateway) {
        return new EditarItemCardapioUseCaseImpl(
                itemRepository, 
                cardapioRepository, 
                restauranteGateway, 
                obterUsuarioLogadoGateway, 
                new NomeItemCardapioNaoPodeSerDuplicadoNoCardapioRule(itemRepository)
        );
    }

    @Bean
    public DeletarItemCardapioUseCase deletarItemCardapioUseCase(ItemCardapioRepository itemRepository,
                                                               CardapioRepository cardapioRepository,
                                                               RestauranteGateway restauranteGateway,
                                                               ObterUsuarioLogadoGateway obterUsuarioLogadoGateway) {
        return new DeletarItemCardapioUseCaseImpl(itemRepository, cardapioRepository, restauranteGateway, obterUsuarioLogadoGateway);
    }

    @Bean
    public BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase(ItemCardapioRepository itemRepository) {
        return new BuscarItemCardapioPorIdUseCaseImpl(itemRepository);
    }

    @Bean
    public CriarCardapioUseCase criarCardapioUseCase(CardapioRepository cardapioRepository,
                                                   RestauranteGateway restauranteGateway,
                                                   ObterUsuarioLogadoGateway obterUsuarioLogadoGateway) {
        List<ValidadorPermissaoCardapioRule<CriacaoCardapioContext>> permissionRules = List.of(
                new ApenasDonoPodeCriarCardapioRule()
        );
        List<ValidadorCardapioRule<CriacaoCardapioContext>> businessRules = List.of(
                new NomeCardapioNaoPodeSerDuplicadoRule(cardapioRepository)
        );
        return new CriarCardapioUseCaseImpl(cardapioRepository, restauranteGateway, obterUsuarioLogadoGateway, permissionRules, businessRules);
    }

    @Bean
    public EditarCardapioUseCase editarCardapioUseCase(CardapioRepository cardapioRepository,
                                                     RestauranteGateway restauranteGateway,
                                                     ObterUsuarioLogadoGateway obterUsuarioLogadoGateway) {
        return new EditarCardapioUseCaseImpl(
                cardapioRepository, 
                restauranteGateway, 
                obterUsuarioLogadoGateway, 
                new NomeCardapioNaoPodeSerDuplicadoRule(cardapioRepository)
        );
    }

    @Bean
    public DeletarCardapioUseCase deletarCardapioUseCase(CardapioRepository cardapioRepository,
                                                       RestauranteGateway restauranteGateway,
                                                       ObterUsuarioLogadoGateway obterUsuarioLogadoGateway) {
        return new DeletarCardapioUseCaseImpl(cardapioRepository, restauranteGateway, obterUsuarioLogadoGateway);
    }

    @Bean
    public BuscarCardapioPorRestauranteUseCase buscarCardapioPorRestauranteUseCase(CardapioRepository cardapioRepository) {
        return new BuscarCardapioPorRestauranteUseCaseImpl(cardapioRepository);
    }
}
