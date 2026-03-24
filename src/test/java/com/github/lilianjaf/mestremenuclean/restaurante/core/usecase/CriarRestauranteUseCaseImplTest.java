package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Endereco;
import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.restaurante.core.dto.DadosCriacaoRestaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.CriacaoRestauranteContext;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ValidadorCriacaoRestauranteRule;
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
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private ValidadorCriacaoRestauranteRule regraDeNegocio;

    private CriarRestauranteUseCaseImpl criarRestauranteUseCase;

    @BeforeEach
    void setUp() {
        lenient().when(transactionGateway.execute(any(Supplier.class))).thenAnswer(invocation -> {
            Supplier<?> supplier = invocation.getArgument(0);
            return supplier.get();
        });

        criarRestauranteUseCase = new CriarRestauranteUseCaseImpl(
                restauranteRepository,
                usuarioGateway,
                obterUsuarioLogadoGateway,
                transactionGateway,
                List.of(permissaoRule),
                List.of(regraDeNegocio)
        );
    }

    @Test
    @DisplayName("Deve criar um restaurante com sucesso quando todos os dados forem válidos")
    void deveCriarRestauranteComSucesso() {
        UUID idDono = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        Usuario dono = mock(Usuario.class);
        Endereco endereco = new Endereco(
                "Rua Teste", "123", null, "Bairro Teste", "Cidade Teste", "12345-678", "TS"
        );
        DadosCriacaoRestaurante dados = new DadosCriacaoRestaurante("Restaurante Teste", endereco, "Italiana", "08:00-22:00", idDono);
        Restaurante restauranteSalvo = new Restaurante(dados.nome(), dados.endereco(), dados.tipoCozinha(), dados.horarioFuncionamento(), dados.idDono());

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(usuarioGateway.buscarPorId(idDono)).thenReturn(Optional.of(dono));
        when(restauranteRepository.salvar(any(Restaurante.class))).thenReturn(restauranteSalvo);

        Restaurante restauranteCriado = criarRestauranteUseCase.executar(dados);

        assertNotNull(restauranteCriado);
        assertEquals(dados.nome(), restauranteCriado.getNome());
        verify(permissaoRule).validar(any(CriacaoRestauranteContext.class));
        verify(regraDeNegocio).validar(any(CriacaoRestauranteContext.class));
        verify(restauranteRepository).salvar(any(Restaurante.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário logado não for encontrado")
    void deveLancarExcecaoQuandoUsuarioLogadoNaoEncontrado() {
        Endereco endereco = new Endereco(
                "Rua Teste", "123", null, "Bairro Teste", "Cidade Teste", "12345-678", "TS"
        );
        DadosCriacaoRestaurante dados = new DadosCriacaoRestaurante("Restaurante Teste", endereco, "Italiana", "08:00-22:00", UUID.randomUUID());

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.empty());

        assertThrows(UsuarioLogadoNaoEncontradoException.class, () -> criarRestauranteUseCase.executar(dados));
    }

    @Test
    @DisplayName("Deve lançar exceção quando uma regra de permissão for violada")
    void deveLancarExcecaoQuandoRegraDePermissaoForViolada() {
        UUID idDono = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        Usuario dono = mock(Usuario.class);
        Endereco endereco = new Endereco(
                "Rua Teste", "123", null, "Bairro Teste", "Cidade Teste", "12345-678", "TS"
        );
        DadosCriacaoRestaurante dados = new DadosCriacaoRestaurante("Restaurante Teste", endereco, "Italiana", "08:00-22:00", idDono);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(usuarioGateway.buscarPorId(idDono)).thenReturn(Optional.of(dono));
        doThrow(new RuntimeException("Permissão negada")).when(permissaoRule).validar(any(CriacaoRestauranteContext.class));

        assertThrows(RuntimeException.class, () -> criarRestauranteUseCase.executar(dados));
    }

    @Test
    @DisplayName("Deve lançar exceção quando uma regra de negócio for violada")
    void deveLancarExcecaoQuandoRegraDeNegocioForViolada() {
        UUID idDono = UUID.randomUUID();
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        Usuario dono = mock(Usuario.class);
        Endereco endereco = new Endereco(
                "Rua Teste", "123", null, "Bairro Teste", "Cidade Teste", "12345-678", "TS"
        );
        DadosCriacaoRestaurante dados = new DadosCriacaoRestaurante("Restaurante Teste", endereco, "Italiana", "08:00-22:00", idDono);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(usuarioGateway.buscarPorId(idDono)).thenReturn(Optional.of(dono));
        doThrow(new RuntimeException("Regra de negócio violada")).when(regraDeNegocio).validar(any(CriacaoRestauranteContext.class));

        assertThrows(RuntimeException.class, () -> criarRestauranteUseCase.executar(dados));
    }
}
