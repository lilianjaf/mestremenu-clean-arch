package com.github.lilianjaf.mestremenuclean.usuario.infra.controller;

public record CriarUsuarioPublicoJson(
        String nome,
        String email,
        String login,
        String senha,
        EnderecoJson endereco
) {
}
