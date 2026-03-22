package com.github.lilianjaf.mestremenuclean.usuario.core.rules;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;

public record ConsultaUsuarioContext(UsuarioBase usuarioLogado, UsuarioBase usuarioBuscado) {
}
