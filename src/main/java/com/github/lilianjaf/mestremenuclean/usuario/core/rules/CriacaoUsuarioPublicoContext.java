package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import java.util.function.BooleanSupplier;

public record CriacaoUsuarioPublicoContext(
        String nome,
        String email,
        String login,
        String senha,
        String nomeTipo,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String cep,
        String uf,
        BooleanSupplier emailJaExiste,
        BooleanSupplier loginJaExiste
) {
}
