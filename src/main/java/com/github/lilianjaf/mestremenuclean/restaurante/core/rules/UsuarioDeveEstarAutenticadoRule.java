package com.github.lilianjaf.mestremenuclean.restaurante.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.restaurante.core.exception.UsuarioNaoAutenticadoException;

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

    public void validar(Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            lancarExcecao();
        }
    }

    private void lancarExcecao() {
        throw new UsuarioNaoAutenticadoException("Usuário não autenticado");
    }
}
