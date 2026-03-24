package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.restaurante.core.dto.DadosCriacaoRestaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.CriacaoRestauranteContext;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ValidadorCriacaoRestauranteRule;
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
class CriarRestauranteUseCaseImplTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;

    @Mock
    private TransactionGateway transactionGateway;

    @Mock
    private ValidadorCriacaoRestauranteRule permissaoRule;

    @Mock
    private ValidadorCriacaoRestauranteRule rule;

    private CriarRestauranteUseCaseImpl usecase;

    @BeforeEach
    void setUp() {
        lenient().when(transactionGateway.execute(any(java.util.function.Supplier.class))).thenAnswer(invocation -> {
            java.util.function.Supplier<?> supplier = invocation.getArgument(0);
            return supplier.get();
        });

        usecase = new CriarRestauranteUseCaseImpl(
                restauranteRepository,
                usuarioGateway,
                obterUsuarioLogadoGateway,
                transactionGateway,
                List.of(permissaoRule),
                List.of(rule)
        );
    }

    @Test
    @DisplayName("Deve criar um restaurante com sucesso")
    void deveCriarRestauranteComSucesso() {
        UUID idDono = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        Usuario dono = mock(Usuario.class);
        com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Endereco endereco = 
                new com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Endereco(
                        "Rua Teste", "123", null, "Bairro Teste", "Cidade Teste", "12345-678", "TS"
                );
        DadosCriacaoRestaurante dados = new DadosCriacaoRestaurante("Restaurante Teste", endereco, "Italiana", "08:00-22:00", idDono);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(usuarioGateway.buscarPorId(idDono)).thenReturn(Optional.of(dono));
        when(restauranteRepository.salvar(any(Restaurante.class))).thenReturn(mock(Restaurante.class));

        usecase.executar(dados);

        verify(permissaoRule).validar(any(CriacaoRestauranteContext.class));
        verify(rule).validar(any(CriacaoRestauranteContext.class));
        verify(restauranteRepository).salvar(any(Restaurante.class));
    }
}
