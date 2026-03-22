package com.github.lilianjaf.mestremenuclean.usuario.core.exception;

public class UsuarioNaoAutenticadoException extends DomainException {
    public UsuarioNaoAutenticadoException(String mensagem) {
        super(mensagem);
    }
}
