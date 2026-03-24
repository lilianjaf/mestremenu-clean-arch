package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ListarRestaurantesRule;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ListarRestaurantesRuleContextDto;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarRestaurantesUseCaseImplTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;

    @Mock
    private ListarRestaurantesRule permissaoRule;

    @Mock
    private ListarRestaurantesRule rule;

    private ListarRestaurantesUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListarRestaurantesUseCaseImpl(
                restauranteRepository,
                obterUsuarioLogadoGateway,
                List.of(permissaoRule),
                List.of(rule)
        );
    }

    @Test
    @DisplayName("Deve listar todos os restaurantes com sucesso quando usuário está logado e regras passam")
    void deveListarRestaurantesComSucesso() {
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        List<Restaurante> restaurantes = List.of(mock(Restaurante.class));

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(restauranteRepository.buscarTodos()).thenReturn(restaurantes);

        List<Restaurante> resultado = useCase.executar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(permissaoRule).validar(any(ListarRestaurantesRuleContextDto.class));
        verify(rule).validar(any(ListarRestaurantesRuleContextDto.class));
        verify(restauranteRepository).buscarTodos();
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário logado não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.empty());

        assertThrows(UsuarioLogadoNaoEncontradoException.class, () -> useCase.executar());

        verify(restauranteRepository, never()).buscarTodos();
        verify(permissaoRule, never()).validar(any());
        verify(rule, never()).validar(any());
    }
}
