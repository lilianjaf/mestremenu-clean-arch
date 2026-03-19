package com.github.lilianjaf.mestremenuclean.usuario.infra.controller;

public record AtualizarUsuarioJson(
        String nome,
        String email,
        EnderecoJson endereco
) {
}