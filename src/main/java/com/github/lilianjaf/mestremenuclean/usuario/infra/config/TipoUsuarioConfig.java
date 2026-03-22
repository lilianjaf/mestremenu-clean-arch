package com.github.lilianjaf.mestremenuclean.usuario.infra.config;

import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.NomeTipoUsuarioDeveSerUnicoRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidarPermissaoDonoRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TipoUsuarioConfig {

    @Bean
    public CriarTipoUsuarioUsecase criarTipoUsuarioUsecase(
            TipoUsuarioRepository tipoUsuarioRepository,
            UsuarioRepository usuarioRepository,
            TransactionGateway transactionGateway) {
        return new CriarTipoUsuarioUsecaseImpl(
                tipoUsuarioRepository,
                usuarioRepository,
                transactionGateway,
                List.of(new ValidarPermissaoDonoRule())
        );
    }

    @Bean
    public AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase(
            TipoUsuarioRepository tipoUsuarioRepository,
            UsuarioRepository usuarioRepository,
            TransactionGateway transactionGateway) {
        return new AtualizarTipoUsuarioUsecaseImpl(
                tipoUsuarioRepository,
                usuarioRepository,
                transactionGateway,
                List.of(new NomeTipoUsuarioDeveSerUnicoRule()),
                List.of(new ValidarPermissaoDonoRule())
        );
    }

    @Bean
    public DeletarTipoUsuarioUsecase deletarTipoUsuarioUsecase(
            TipoUsuarioRepository tipoUsuarioRepository,
            UsuarioRepository usuarioRepository,
            TransactionGateway transactionGateway) {
        return new DeletarTipoUsuarioUsecaseImpl(tipoUsuarioRepository, usuarioRepository, transactionGateway);
    }
}