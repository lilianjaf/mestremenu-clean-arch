package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.ItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DeletarItemCardapioRuleContextDto;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CardapioException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.UsuarioLogadoNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.CardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ItemCardapioRepository;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.RestauranteGateway;
import com.github.lilianjaf.mestremenuclean.cardapio.core.rules.ValidadorPermissaoCardapioRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarItemCardapioUseCaseImplTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;

    @Mock
    private CardapioRepository cardapioRepository;

    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;

    @Mock
    private ValidadorPermissaoCardapioRule<DeletarItemCardapioRuleContextDto> permissaoRule;

    @Mock
    private ValidadorPermissaoCardapioRule<DeletarItemCardapioRuleContextDto> businessRule;

    private DeletarItemCardapioUseCaseImpl deletarItemCardapioUseCase;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        deletarItemCardapioUseCase = new DeletarItemCardapioUseCaseImpl(
                itemCardapioRepository,
                cardapioRepository,
                restauranteGateway,
                obterUsuarioLogadoGateway,
                List.of(permissaoRule),
                List.of(businessRule)
        );
    }

    @Test
    @DisplayName("Deve deletar um item de cardápio com sucesso")
    void deveDeletarItemCardapioComSucesso() {
        UUID idItem = UUID.randomUUID();
        UUID idCardapio = UUID.randomUUID();
        UUID idRestaurante = UUID.randomUUID();
        UUID idDono = UUID.randomUUID();
        ItemCardapio item = new ItemCardapio("Burger", "Top", BigDecimal.TEN, true, "img.png", idCardapio);
        Cardapio cardapio = new Cardapio("Menu", idRestaurante, Collections.emptyList());
        Restaurante restaurante = new Restaurante(idRestaurante, idDono);
        Usuario usuarioLogado = new Usuario(idDono, TipoNativo.DONO);

        when(itemCardapioRepository.findById(idItem)).thenReturn(Optional.of(item));
        when(cardapioRepository.findById(idCardapio)).thenReturn(Optional.of(cardapio));
        when(restauranteGateway.buscarPorId(idRestaurante)).thenReturn(Optional.of(restaurante));
        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));

        deletarItemCardapioUseCase.executar(idItem);

        verify(permissaoRule).validar(any());
        verify(businessRule).validar(any());
        verify(itemCardapioRepository).deletar(idItem);
    }

    @Test
    @DisplayName("Deve lançar exceção quando item não for encontrado para exclusão")
    void deveLancarExcecaoQuandoItemNaoForEncontradoParaExclusao() {
        UUID idItem = UUID.randomUUID();

        when(itemCardapioRepository.findById(idItem)).thenReturn(Optional.empty());

        assertThrows(CardapioException.class, () -> deletarItemCardapioUseCase.executar(idItem));

        verify(itemCardapioRepository, never()).deletar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando cardápio associado não for encontrado")
    void deveLancarExcecaoQuandoCardapioAssociadoNaoForEncontrado() {
        UUID idItem = UUID.randomUUID();
        UUID idCardapio = UUID.randomUUID();
        ItemCardapio item = new ItemCardapio("Burger", "Top", BigDecimal.TEN, true, "img.png", idCardapio);

        when(itemCardapioRepository.findById(idItem)).thenReturn(Optional.of(item));
        when(cardapioRepository.findById(idCardapio)).thenReturn(Optional.empty());

        assertThrows(CardapioException.class, () -> deletarItemCardapioUseCase.executar(idItem));

        verify(itemCardapioRepository, never()).deletar(any());
    }
}
