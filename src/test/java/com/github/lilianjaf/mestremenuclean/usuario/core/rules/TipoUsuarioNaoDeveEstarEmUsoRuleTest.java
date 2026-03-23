package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.TipoUsuarioEmUsoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TipoUsuarioNaoDeveEstarEmUsoRuleTest {

    private final TipoUsuarioNaoDeveEstarEmUsoRule rule = new TipoUsuarioNaoDeveEstarEmUsoRule();

    @Test
    @DisplayName("Deve permitir quando tipo de usuario nao esta em uso")
    void devePermitirQuandoNaoEstaEmUso() {
        ExclusaoTipoUsuarioContext context = criarContexto(() -> false, "estagiario");
        assertDoesNotThrow(() -> rule.validar(context));
    }

    @Test
    @DisplayName("Deve lancar excecao quando tipo de usuario esta em uso")
    void deveLancarExcecaoQuandoEstaEmUso() {
        ExclusaoTipoUsuarioContext context = criarContexto(() -> true, "estagiario");
        assertThrows(TipoUsuarioEmUsoException.class, () -> rule.validar(context));
    }

    @Test
    @DisplayName("Deve lancar excecao mesmo se tipo de usuario for opcional vazio mas estiver em uso")
    void deveLancarExcecaoMesmoSemTipoNoContexto() {
        ExclusaoTipoUsuarioContext context = new ExclusaoTipoUsuarioContext(Optional.empty(), () -> true, null);
        assertThrows(TipoUsuarioEmUsoException.class, () -> rule.validar(context));
    }

    private ExclusaoTipoUsuarioContext criarContexto(BooleanSupplier estaEmUso, String nomeTipo) {
        TipoUsuario tipo = new TipoUsuario(nomeTipo, TipoNativo.CLIENTE);
        return new ExclusaoTipoUsuarioContext(Optional.of(tipo), estaEmUso, null);
    }
}
