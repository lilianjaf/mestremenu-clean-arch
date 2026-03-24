package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.BuscarRestauranteRule;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.BuscarRestauranteRuleContextDto;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioLogadoNaoEncontradoException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarRestaurantePorIdUseCaseImplTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;

    @Mock
    private BuscarRestauranteRule permissaoRule;

    @Mock
    private BuscarRestauranteRule regraDeNegocio;

    private BuscarRestaurantePorIdUseCaseImpl buscarRestaurantePorIdUseCase;

    @BeforeEach
    void setUp() {
        buscarRestaurantePorIdUseCase = new BuscarRestaurantePorIdUseCaseImpl(
                restauranteRepository,
                obterUsuarioLogadoGateway,
                List.of(permissaoRule),
                List.of(regraDeNegocio)
        );
    }

    @Test
    @DisplayName("Deve buscar um restaurante por ID com sucesso quando os dados forem válidos")
    void deveBuscarRestaurantePorIdComSucesso() {
        UUID id = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        Restaurante restaurante = mock(Restaurante.class);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));

        Restaurante resultado = buscarRestaurantePorIdUseCase.executar(id);

        assertNotNull(resultado);
        assertEquals(restaurante, resultado);
        verify(permissaoRule).validar(any(BuscarRestauranteRuleContextDto.class));
        verify(regraDeNegocio).validar(any(BuscarRestauranteRuleContextDto.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário logado não for encontrado")
    void deveLancarExcecaoQuandoUsuarioLogadoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.empty());

        assertThrows(UsuarioLogadoNaoEncontradoException.class, () -> buscarRestaurantePorIdUseCase.executar(id));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ID do restaurante for nulo")
    void deveLancarExcecaoQuandoIdForNulo() {
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));

        assertThrows(DomainException.class, () -> buscarRestaurantePorIdUseCase.executar(null));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o restaurante não for encontrado")
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        UUID id = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(restauranteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> buscarRestaurantePorIdUseCase.executar(id));
    }

    @Test
    @DisplayName("Deve lançar exceção quando uma regra de permissão for violada")
    void deveLancarExcecaoQuandoRegraDePermissaoForViolada() {
        UUID id = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        Restaurante restaurante = mock(Restaurante.class);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));
        doThrow(new RuntimeException("Permissão negada")).when(permissaoRule).validar(any(BuscarRestauranteRuleContextDto.class));

        assertThrows(RuntimeException.class, () -> buscarRestaurantePorIdUseCase.executar(id));
    }

    @Test
    @DisplayName("Deve lançar exceção quando uma regra de negócio for violada")
    void deveLancarExcecaoQuandoRegraDeNegocioForViolada() {
        UUID id = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        Restaurante restaurante = mock(Restaurante.class);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));
        doThrow(new RuntimeException("Regra de negócio violada")).when(regraDeNegocio).validar(any(BuscarRestauranteRuleContextDto.class));

        assertThrows(RuntimeException.class, () -> buscarRestaurantePorIdUseCase.executar(id));
    }
}
