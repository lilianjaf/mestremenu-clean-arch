package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

import java.util.UUID;

public interface CriarUsuarioUsecase {
    UUID criar(
            UsuarioBase usuarioLogado,
            String nome, String email, String login, String senhaPura,
            String nomeTipoDesejado, TipoNativo tipoNativoDesejado,
            String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf);
}
