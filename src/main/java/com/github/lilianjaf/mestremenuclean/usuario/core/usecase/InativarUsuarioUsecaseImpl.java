package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;

import java.util.UUID;

public class InativarUsuarioUsecaseImpl implements InativarUsuarioUsecase {
    private final BuscarUsuarioUsecase buscarUsuarioUsecase;
    private final UsuarioRepository repository;
    private final TransactionGateway transactionGateway;

    public InativarUsuarioUsecaseImpl(BuscarUsuarioUsecase buscarUsuarioUsecase,
                                  UsuarioRepository usuarioRepository,
                                  TransactionGateway transactionGateway) {
        this.buscarUsuarioUsecase = buscarUsuarioUsecase;
        this.repository = usuarioRepository;
        this.transactionGateway = transactionGateway;
    }

    @Override
    public void inativar(UUID id) {
        transactionGateway.execute(() -> {
            UsuarioBase usuario = buscarUsuarioUsecase.buscarEntidade(id);

            usuario.desativar();

            repository.salvar(usuario);
        });
    }
}