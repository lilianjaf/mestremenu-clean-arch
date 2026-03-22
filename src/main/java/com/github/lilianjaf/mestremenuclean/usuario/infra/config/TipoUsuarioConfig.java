package com.github.lilianjaf.mestremenuclean.usuario.infra.config;

import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.AtualizarTipoUsuarioUsecase;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.CriarTipoUsuarioUsecase;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.DeletarTipoUsuarioUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TipoUsuarioConfig {

    @Bean
    public CriarTipoUsuarioUsecase criarTipoUsuarioUsecase(
            TipoUsuarioRepository tipoUsuarioRepository,
            TransactionGateway transactionGateway) {
        return new CriarTipoUsuarioUsecase(tipoUsuarioRepository, transactionGateway);
    }

    @Bean
    public AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase(
            TipoUsuarioRepository tipoUsuarioRepository,
            TransactionGateway transactionGateway) {
        return new AtualizarTipoUsuarioUsecase(tipoUsuarioRepository, transactionGateway);
    }

    @Bean
    public DeletarTipoUsuarioUsecase deletarTipoUsuarioUsecase(
            TipoUsuarioRepository tipoUsuarioRepository,
            UsuarioRepository usuarioRepository,
            TransactionGateway transactionGateway) {
        return new DeletarTipoUsuarioUsecase(tipoUsuarioRepository, usuarioRepository, transactionGateway);
    }
}