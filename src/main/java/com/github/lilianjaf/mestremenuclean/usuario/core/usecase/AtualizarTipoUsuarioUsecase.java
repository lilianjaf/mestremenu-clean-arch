package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import java.util.UUID;

public interface AtualizarTipoUsuarioUsecase {
    void atualizar(String loginUsuarioLogado, UUID id, String novoNome);
}
