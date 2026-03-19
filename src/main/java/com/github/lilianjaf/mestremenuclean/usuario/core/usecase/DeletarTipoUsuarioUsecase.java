package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegistroNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;

import java.util.UUID;

public class DeletarTipoUsuarioUsecase {

    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    public DeletarTipoUsuarioUsecase(TipoUsuarioRepository tipoUsuarioRepository, UsuarioRepository usuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void deletar(UUID id) {
        TipoUsuario tipo = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Tipo de usuário não encontrado."));

        if (usuarioRepository.existeUsuarioComTipo(id)) {
            throw new RegraDeNegocioException("Não é possível excluir o tipo '" + tipo.getNome() + "' pois existem usuários vinculados a ele.");
        }

        tipoUsuarioRepository.deletar(tipo);
    }
}