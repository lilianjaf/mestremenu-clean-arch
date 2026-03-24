package com.github.lilianjaf.mestremenuclean.cardapio.core.rules;

import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.BuscarCardapioPorRestauranteRuleContextDto;
import com.github.lilianjaf.mestremenuclean.cardapio.core.dto.BuscarItemCardapioPorIdRuleContextDto;
import com.github.lilianjaf.mestremenuclean.cardapio.core.exception.UsuarioNaoAutenticadoException;

public class UsuarioDeveEstarAutenticadoRule implements 
    ValidadorPermissaoCardapioRule<Object> {
    
    @Override
    public void validar(Object context) {
        if (context instanceof BuscarCardapioPorRestauranteRuleContextDto dto) {
            validarContexto(dto.isUsuarioAutenticado());
        } else if (context instanceof BuscarItemCardapioPorIdRuleContextDto dto) {
            validarContexto(dto.isUsuarioAutenticado());
        }
    }

    private void validarContexto(boolean isAutenticado) {
        if (!isAutenticado) {
            throw new UsuarioNaoAutenticadoException("Usuário não autenticado");
        }
    }

    public static ValidadorPermissaoCardapioRule<BuscarCardapioPorRestauranteRuleContextDto> paraBuscarCardapio() {
        return context -> new UsuarioDeveEstarAutenticadoRule().validar(context);
    }

    public static ValidadorPermissaoCardapioRule<BuscarItemCardapioPorIdRuleContextDto> paraBuscarItem() {
        return context -> new UsuarioDeveEstarAutenticadoRule().validar(context);
    }
}
