package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.BuscarRestauranteRule;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.BuscarRestauranteRuleContextDto;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ListarRestaurantesRule;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ListarRestaurantesRuleContextDto;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioNaoAutenticadoException;

public class UsuarioDeveEstarAutenticadoRule implements ValidadorCriacaoTipoUsuarioRule, ValidadorConsultaUsuarioRule, ValidadorPermissaoAtualizacaoUsuarioRule, ValidadorPermissaoRule, ValidadorExclusaoTipoUsuarioRule, ValidadorInativacaoUsuarioRule, ValidadorAtualizacaoTipoUsuarioRule, ValidadorCriacaoUsuarioRule, BuscarRestauranteRule, ListarRestaurantesRule {
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
    @Override
    public void validar(InativacaoUsuarioContext context) {
        if (!context.isUsuarioLogadoAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(ExclusaoTipoUsuarioContext context) {
        if (!context.isUsuarioLogadoAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(CriacaoTipoUsuarioContext context) {
        if (!context.isUsuarioLogadoAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(ConsultaUsuarioContext context) {
        if (!context.isUsuarioLogadoAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(AtualizacaoUsuarioContext context) {
        if (!context.isUsuarioLogadoAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(AtualizacaoTipoUsuarioContext context) {
        if (!context.isUsuarioLogadoAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(CriacaoUsuarioContext context) {
        if (!context.isUsuarioLogadoAutenticado()) {
            lancarExcecao();
        }
    }

    @Override
    public void validar(UsuarioBase usuarioLogado) {
        if (usuarioLogado == null) {
            lancarExcecao();
        }
    }

    private void lancarExcecao() {
        throw new UsuarioNaoAutenticadoException("Usuário não autenticado");
    }
}
