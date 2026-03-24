package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.AtualizarRestauranteRuleContextDto;
import com.github.lilianjaf.mestremenuclean.restaurante.core.dto.DadosAtualizacaoRestaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.AtualizarRestauranteRule;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarRestauranteUseCaseImplTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;

    @Mock
    private AtualizarRestauranteRule permissaoRule;

    @Mock
    private AtualizarRestauranteRule rule;

    private AtualizarRestauranteUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        usecase = new AtualizarRestauranteUseCaseImpl(
                restauranteRepository,
                obterUsuarioLogadoGateway,
                List.of(permissaoRule),
                List.of(rule)
        );
    }

    @Test
    @DisplayName("Deve atualizar um restaurante com sucesso")
    void deveAtualizarRestauranteComSucesso() {
        UUID id = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        Restaurante restaurante = mock(Restaurante.class);
        DadosAtualizacaoRestaurante dados = mock(DadosAtualizacaoRestaurante.class);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));
        when(restauranteRepository.salvar(any(Restaurante.class))).thenReturn(restaurante);

        usecase.executar(id, dados);

        verify(permissaoRule).validar(any(AtualizarRestauranteRuleContextDto.class));
        verify(rule).validar(any(AtualizarRestauranteRuleContextDto.class));
        verify(restaurante).atualizar(any(), any(), any(), any());
        verify(restauranteRepository).salvar(restaurante);
    }
}
