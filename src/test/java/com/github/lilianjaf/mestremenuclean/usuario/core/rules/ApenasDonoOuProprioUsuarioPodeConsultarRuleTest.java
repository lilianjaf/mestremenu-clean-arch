package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Dono;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.AcessoNegadoConsultaUsuarioException;
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
class ApenasDonoOuProprioUsuarioPodeConsultarRuleTest {

    @Mock
    private UsuarioBase usuarioLogado;

    @Mock
    private UsuarioBase usuarioBuscado;

    private final ApenasDonoOuProprioUsuarioPodeConsultarRule rule = new ApenasDonoOuProprioUsuarioPodeConsultarRule();

    @Test
    @DisplayName("Deve permitir consulta quando usuario logado eh dono")
    void devePermitirConsultaQuandoUsuarioLogadoEhDono() {
        Dono donoLogado = mock(Dono.class);
        when(donoLogado.getId()).thenReturn(UUID.randomUUID());
        assertDoesNotThrow(() -> rule.validar(donoLogado, usuarioBuscado));
    }

    @Test
    @DisplayName("Deve permitir consulta quando usuario logado eh o proprio buscado")
    void devePermitirConsultaQuandoEhOMesmoUsuario() {
        UUID id = UUID.randomUUID();
        when(usuarioLogado.getId()).thenReturn(id);
        when(usuarioBuscado.getId()).thenReturn(id);

        assertDoesNotThrow(() -> rule.validar(usuarioLogado, usuarioBuscado));
    }

    @Test
    @DisplayName("Deve lancar excecao quando nao tem permissao para consultar")
    void deveLancarExcecaoQuandoNaoTemPermissao() {
        when(usuarioLogado.getId()).thenReturn(UUID.randomUUID());
        when(usuarioBuscado.getId()).thenReturn(UUID.randomUUID());

        assertThrows(AcessoNegadoConsultaUsuarioException.class, () -> rule.validar(usuarioLogado, usuarioBuscado));
    }

    @Test
    @DisplayName("Deve retornar sem erro se o usuario logado for nulo")
    void deveRetornarSeUsuarioLogadoForNulo() {
        assertDoesNotThrow(() -> rule.validar(null, usuarioBuscado));
    }
}
