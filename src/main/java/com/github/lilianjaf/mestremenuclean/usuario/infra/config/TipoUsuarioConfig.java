package com.github.lilianjaf.mestremenuclean.usuario.infra.config;

import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TipoUsuarioConfig {

    @Bean
    public CriarTipoUsuarioUsecase criarTipoUsuarioUsecase(
            TipoUsuarioRepository tipoUsuarioRepository,
            TransactionGateway transactionGateway) {
        return new CriarTipoUsuarioUsecaseImpl(tipoUsuarioRepository, transactionGateway);
    }

    @Bean
    public AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase(
            TipoUsuarioRepository tipoUsuarioRepository,
            TransactionGateway transactionGateway) {
        return new AtualizarTipoUsuarioUsecaseImpl(tipoUsuarioRepository, transactionGateway);
    }

    @Bean
    public DeletarTipoUsuarioUsecase deletarTipoUsuarioUsecase(
            TipoUsuarioRepository tipoUsuarioRepository,
            UsuarioRepository usuarioRepository,
            TransactionGateway transactionGateway) {
        return new DeletarTipoUsuarioUsecaseImpl(tipoUsuarioRepository, usuarioRepository, transactionGateway);
    }
}