package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioNaoAutenticadoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.*;

public class UsuarioDeveEstarAutenticadoRule implements ValidadorCriacaoRestauranteRule, AtualizarRestauranteRule, InativarRestauranteRule, ListarRestaurantesRule, BuscarRestauranteRule {
    @Override
    public void validar(CriacaoRestauranteContext context) {
        if (!context.isUsuarioAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(AtualizarRestauranteRuleContextDto context) {
        if (!context.isUsuarioAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(InativacaoRestauranteContext context) {
        if (!context.isUsuarioAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(ListarRestaurantesRuleContextDto context) {
        if (!context.isUsuarioAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(BuscarRestauranteRuleContextDto context) {
        if (!context.isUsuarioAutenticado()) {
            lancarExcecao();
        }
    }

    public void validar(UsuarioBase usuarioLogado) {
        if (usuarioLogado == null) {
            lancarExcecao();
        }
    }

    private void lancarExcecao() {
        throw new UsuarioNaoAutenticadoException("Usuário não autenticado");
    }
}
