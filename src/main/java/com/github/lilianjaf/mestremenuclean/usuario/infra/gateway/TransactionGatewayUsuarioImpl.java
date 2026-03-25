package com.github.lilianjaf.mestremenuclean.usuario.infra.gateway;

import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Component
public class TransactionGatewayUsuarioImpl implements TransactionGateway {

    @Override
    @Transactional
    public <T> T execute(Supplier<T> operation) {
        return operation.get();
    }

    @Override
    @Transactional
    public void execute(Runnable operation) {
        operation.run();
    }
}