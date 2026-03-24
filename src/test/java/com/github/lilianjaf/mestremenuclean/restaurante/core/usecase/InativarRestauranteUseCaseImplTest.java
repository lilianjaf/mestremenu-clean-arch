package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ApenasDonoPodeInativarProprioRestauranteRule;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.InativacaoRestauranteContext;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.RestauranteDeveEstarAtivoRule;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InativarRestauranteUseCaseImplTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;

    @Mock
    private TransactionGateway transactionGateway;

    @Mock
    private ApenasDonoPodeInativarProprioRestauranteRule permissaoRule;

    @Mock
    private RestauranteDeveEstarAtivoRule rule;

    private InativarRestauranteUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(transactionGateway).execute(any(Runnable.class));

        usecase = new InativarRestauranteUseCaseImpl(
                restauranteRepository,
                obterUsuarioLogadoGateway,
                transactionGateway,
                List.of(permissaoRule),
                List.of(rule)
        );
    }

    @Test
    @DisplayName("Deve inativar um restaurante com sucesso")
    void deveInativarRestauranteComSucesso() {
        UUID id = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        Restaurante restaurante = mock(Restaurante.class);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));

        usecase.executar(id);

        verify(permissaoRule).validar(any(InativacaoRestauranteContext.class));
        verify(rule).validar(any(InativacaoRestauranteContext.class));
        verify(restaurante).inativar();
        verify(restauranteRepository).salvar(restaurante);
    }
}
