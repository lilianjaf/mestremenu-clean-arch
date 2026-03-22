package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegistroNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.RegraDeNegocioException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;

import java.util.UUID;

public class DeletarTipoUsuarioUsecase {

    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final TransactionGateway transactionGateway;

    public DeletarTipoUsuarioUsecase(TipoUsuarioRepository tipoUsuarioRepository,
                                     UsuarioRepository usuarioRepository,
                                     TransactionGateway transactionGateway) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.transactionGateway = transactionGateway;
    }

    public void deletar(UUID id) {
        transactionGateway.execute(() -> {
            TipoUsuario tipo = tipoUsuarioRepository.findById(id)
                    .orElseThrow(() -> new RegistroNaoEncontradoException("Tipo de usuário não encontrado."));

            if (usuarioRepository.existeUsuarioComTipo(id)) {
                throw new RegraDeNegocioException("Não é possível excluir o tipo '" + tipo.getNome() + "' pois existem usuários vinculados a ele.");
            }

            tipoUsuarioRepository.deletar(tipo);
        });
    }
}