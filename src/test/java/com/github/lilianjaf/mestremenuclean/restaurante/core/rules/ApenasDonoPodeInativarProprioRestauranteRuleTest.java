package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.exception.InativacaoRestauranteNaoAutorizadaException;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApenasDonoPodeInativarProprioRestauranteRuleTest {

    private final ApenasDonoPodeInativarProprioRestauranteRule rule = new ApenasDonoPodeInativarProprioRestauranteRule();

    @Test
    @DisplayName("Deve permitir inativação quando usuário é dono do restaurante")
    void devePermitirInativacaoQuandoUsuarioEhDono() {
        UUID donoId = UUID.randomUUID();
        Dono donoLogado = mock(Dono.class);
        when(donoLogado.getId()).thenReturn(donoId);
        when(donoLogado.isDono()).thenReturn(true);

        Restaurante restaurante = mock(Restaurante.class);
        when(restaurante.getIdDono()).thenReturn(donoId);

        InativacaoRestauranteContext context = new InativacaoRestauranteContext(donoLogado, restaurante);
        assertDoesNotThrow(() -> rule.validar(context));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não é dono (perfil)")
    void deveLancarExcecaoQuandoUsuarioNaoTemPerfilDono() {
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);
        when(usuarioLogado.isDono()).thenReturn(false);

        Restaurante restaurante = mock(Restaurante.class);

        InativacaoRestauranteContext context = new InativacaoRestauranteContext(usuarioLogado, restaurante);
        assertThrows(InativacaoRestauranteNaoAutorizadaException.class, () -> rule.validar(context));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário é dono mas de outro restaurante")
    void deveLancarExcecaoQuandoUsuarioEhDonoDeOutroRestaurante() {
        Dono donoLogado = mock(Dono.class);
        when(donoLogado.getId()).thenReturn(UUID.randomUUID());
        when(donoLogado.isDono()).thenReturn(true);

        Restaurante restaurante = mock(Restaurante.class);
        when(restaurante.getIdDono()).thenReturn(UUID.randomUUID());

        InativacaoRestauranteContext context = new InativacaoRestauranteContext(donoLogado, restaurante);
        assertThrows(InativacaoRestauranteNaoAutorizadaException.class, () -> rule.validar(context));
    }
}
