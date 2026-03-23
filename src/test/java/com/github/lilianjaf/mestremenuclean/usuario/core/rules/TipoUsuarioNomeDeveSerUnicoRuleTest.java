package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.TipoUsuarioJaCadastradoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TipoUsuarioNomeDeveSerUnicoRuleTest {

    private final TipoUsuarioNomeDeveSerUnicoRule rule = new TipoUsuarioNomeDeveSerUnicoRule();

    @Test
    @DisplayName("Deve permitir quando nome do tipo de usuario nao existe")
    void devePermitirQuandoNomeNaoExiste() {
        CriacaoTipoUsuarioContext context = new CriacaoTipoUsuarioContext("estagiario", false);
        assertDoesNotThrow(() -> rule.validar(context));
    }

    @Test
    @DisplayName("Deve lancar excecao quando nome do tipo de usuario ja existe")
    void deveLancarExcecaoQuandoNomeJaExiste() {
        CriacaoTipoUsuarioContext context = new CriacaoTipoUsuarioContext("estagiario", true);
        assertThrows(TipoUsuarioJaCadastradoException.class, () -> rule.validar(context));
    }
}
