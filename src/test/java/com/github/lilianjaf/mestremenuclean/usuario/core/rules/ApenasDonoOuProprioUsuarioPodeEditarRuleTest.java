package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.EdicaoUsuarioNaoAutorizadaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApenasDonoOuProprioUsuarioPodeEditarRuleTest {

    @Mock
    private UsuarioBase usuarioLogado;

    @Mock
    private UsuarioBase usuarioSendoEditado;

    private final ApenasDonoOuProprioUsuarioPodeEditarRule rule = new ApenasDonoOuProprioUsuarioPodeEditarRule();

    @Test
    @DisplayName("Deve permitir edicao quando o usuario logado eh dono")
    void devePermitirEdicaoQuandoUsuarioLogadoEhDono() {
        Dono donoLogado = mock(Dono.class);
        when(donoLogado.getId()).thenReturn(UUID.randomUUID());
        assertDoesNotThrow(() -> rule.validar(donoLogado, usuarioSendoEditado));
    }

    @Test
    @DisplayName("Deve permitir edicao quando o usuario logado eh o proprio usuario sendo editado")
    void devePermitirEdicaoQuandoEhOProprioUsuario() {
        UUID idComum = UUID.randomUUID();
        when(usuarioLogado.getId()).thenReturn(idComum);
        when(usuarioSendoEditado.getId()).thenReturn(idComum);

        assertDoesNotThrow(() -> rule.validar(usuarioLogado, usuarioSendoEditado));
    }

    @Test
    @DisplayName("Deve lancar excecao quando usuario nao eh dono nem o proprio editado")
    void deveLancarExcecaoQuandoNaoTemPermissao() {
        when(usuarioLogado.getId()).thenReturn(UUID.randomUUID());
        when(usuarioSendoEditado.getId()).thenReturn(UUID.randomUUID());

        assertThrows(EdicaoUsuarioNaoAutorizadaException.class, () -> rule.validar(usuarioLogado, usuarioSendoEditado));
    }

    @Test
    @DisplayName("Deve permitir se o usuario sendo editado for nulo")
    void devePermitirSeUsuarioSendoEditadoForNulo() {
        assertDoesNotThrow(() -> rule.validar(usuarioLogado, null));
    }
}
