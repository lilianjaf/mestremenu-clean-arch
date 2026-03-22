package com.github.lilianjaf.mestremenuclean.usuario.core.exception;

public class EmailUsuarioJaEmUsoException extends RegraDeNegocioException {
    public EmailUsuarioJaEmUsoException(String mensagem) {
        super(mensagem);
    }
}
