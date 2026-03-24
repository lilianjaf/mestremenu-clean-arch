package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.BuscarRestauranteRule;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.BuscarRestauranteRuleContextDto;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.ObterUsuarioLogadoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarRestaurantePorIdUseCaseImplTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;

    @Mock
    private BuscarRestauranteRule permissaoRule;

    @Mock
    private BuscarRestauranteRule rule;

    private BuscarRestaurantePorIdUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        usecase = new BuscarRestaurantePorIdUseCaseImpl(
                restauranteRepository,
                obterUsuarioLogadoGateway,
                List.of(permissaoRule),
                List.of(rule)
        );
    }

    @Test
    @DisplayName("Deve buscar um restaurante por id com sucesso")
    void deveBuscarRestauranteComSucesso() {
        UUID id = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        Restaurante restaurante = mock(Restaurante.class);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));

        Restaurante resultado = usecase.executar(id);

        assertNotNull(resultado);
        assertEquals(restaurante, resultado);
        verify(permissaoRule).validar(any(BuscarRestauranteRuleContextDto.class));
        verify(rule).validar(any(BuscarRestauranteRuleContextDto.class));
        verify(restauranteRepository).findById(id);
    }
}
