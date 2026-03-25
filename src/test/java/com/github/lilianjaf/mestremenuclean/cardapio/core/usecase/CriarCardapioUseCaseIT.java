package com.github.lilianjaf.mestremenuclean.cardapio.core.usecase;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Cardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosCriacaoCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.DadosCriacaoItemCardapio;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.CriacaoCardapioNaoAutorizadaException;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.CardapioRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.api.RestauranteIntegrationDto;
import com.github.lilianjaf.mestremenuclean.restaurante.core.api.RestauranteModuleFacade;
import com.github.lilianjaf.mestremenuclean.usuario.core.api.UsuarioIntegrationDto;
import com.github.lilianjaf.mestremenuclean.usuario.core.api.UsuarioModuleFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
class CriarCardapioUseCaseIT {

    @Autowired
    private CriarCardapioUseCase usecase;

    @Autowired
    private CardapioRepository cardapioRepository;

    @MockBean
    private RestauranteModuleFacade restauranteFacade;

    @MockBean
    private UsuarioModuleFacade usuarioFacade;

    private UUID idDono;
    private UUID idRestaurante;
    private String username = "dono.restaurante";

    @BeforeEach
    void setUp() {
        idDono = UUID.randomUUID();
        idRestaurante = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, null, List.of())
        );

        when(usuarioFacade.buscarPorUsuario(username)).thenReturn(
                new UsuarioIntegrationDto(idDono, "Dono", "DONO", true)
        );

        when(restauranteFacade.buscarPorId(idRestaurante)).thenReturn(
                new RestauranteIntegrationDto(idRestaurante, idDono, true)
        );
    }

    @Test
    @DisplayName("Deve criar e persistir um cardápio com sucesso quando os dados são válidos")
    void deveCriarEPersistirCardapioComSucesso() {
        DadosCriacaoItemCardapio itemDto = new DadosCriacaoItemCardapio(
                "Burger", "Com queijo", BigDecimal.valueOf(25), true, "burger.jpg", null);
        DadosCriacaoCardapio dados = new DadosCriacaoCardapio("Menu Principal", idRestaurante, List.of(itemDto));

        Cardapio resultado = usecase.executar(dados);

        assertNotNull(resultado.getId());
        Cardapio persistido = cardapioRepository.findById(resultado.getId()).orElseThrow();
        assertEquals("Menu Principal", persistido.getNome());
        assertEquals(idRestaurante, persistido.getIdRestaurante());
        assertEquals(1, persistido.getItens().size());
        assertEquals("Burger", persistido.getItens().get(0).getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção e não persistir quando o usuário não for o dono do restaurante")
    void deveLancarExcecaoQuandoUsuarioNaoForDono() {
        UUID outroIdDono = UUID.randomUUID();
        when(usuarioFacade.buscarPorUsuario(username)).thenReturn(
                new UsuarioIntegrationDto(outroIdDono, "Outro", "DONO", true)
        );

        DadosCriacaoItemCardapio itemDto = new DadosCriacaoItemCardapio(
                "Burger", "Com queijo", BigDecimal.valueOf(25), true, "burger.jpg", null);
        DadosCriacaoCardapio dados = new DadosCriacaoCardapio("Menu Invalido", idRestaurante, List.of(itemDto));

        assertThrows(CriacaoCardapioNaoAutorizadaException.class, () -> usecase.executar(dados));
        
        boolean existe = cardapioRepository.existeNomeParaRestaurante("Menu Invalido", idRestaurante);
        assertFalse(existe);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome do cardápio já existir para o restaurante")
    void deveLancarExcecaoQuandoNomeDuplicado() {
        DadosCriacaoItemCardapio itemDto = new DadosCriacaoItemCardapio(
                "Burger", "Com queijo", BigDecimal.valueOf(25), true, "burger.jpg", null);
        DadosCriacaoCardapio dados = new DadosCriacaoCardapio("Menu Duplicado", idRestaurante, List.of(itemDto));

        usecase.executar(dados);

        assertThrows(RuntimeException.class, () -> usecase.executar(dados));
    }
}
