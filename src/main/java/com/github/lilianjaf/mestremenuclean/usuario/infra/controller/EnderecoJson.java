package com.github.lilianjaf.mestremenuclean.usuario.infra.controller;

public record EnderecoJson(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String cep,
        String uf
) {
}