package com.github.lilianjaf.mestremenuclean.restaurante.core.exception;

import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;

public class UsuarioLogadoNaoEncontradoException extends RegraDeNegocioException {
    public UsuarioLogadoNaoEncontradoException(String message) {
        super(message);
    }
}
