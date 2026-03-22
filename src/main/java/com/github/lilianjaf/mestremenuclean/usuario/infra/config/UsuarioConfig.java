package com.github.lilianjaf.mestremenuclean.usuario.infra.config;

import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.CodificadorDeSenha;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ForcarTipoClienteRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorCriacaoUsuarioRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidarPermissaoDonoRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UsuarioConfig {

    @Bean
    public CriarUsuarioPublicoUseCase criarUsuarioPublicoUseCase(
            UsuarioRepository usuarioRepository,
            TipoUsuarioRepository tipoUsuarioRepository,
            TransactionGateway transactionGateway,
            CodificadorDeSenha codificadorDeSenha) {

        List<ValidadorCriacaoUsuarioRule> rules = List.of(
                new ForcarTipoClienteRule()
        );

        return new CriarUsuarioPublicoUseCaseImpl(usuarioRepository,
                tipoUsuarioRepository,
                rules,
                transactionGateway,
                codificadorDeSenha);
    }

    @Bean
    public CriarUsuarioUsecase criarUsuarioUsecase(
            UsuarioRepository usuarioRepository,
            TipoUsuarioRepository tipoUsuarioRepository,
            CodificadorDeSenha codificadorDeSenha,
            TransactionGateway transactionGateway) {
        return new CriarUsuarioUsecaseImpl(
                usuarioRepository,
                tipoUsuarioRepository,
                codificadorDeSenha,
                transactionGateway,
                List.of(new ValidarPermissaoDonoRule())
        );
    }

    @Bean
    public BuscarUsuarioUsecase buscarUsuarioUsecase(UsuarioRepository usuarioRepository) {
        return new BuscarUsuarioUsecaseImpl(usuarioRepository);
    }

    @Bean
    public AtualizarUsuarioUsecase atualizarUsuarioUsecase(
            BuscarUsuarioUsecase buscarUsuarioUsecase,
            UsuarioRepository usuarioRepository,
            TransactionGateway transactionGateway) {
        return new AtualizarUsuarioUsecaseImpl(buscarUsuarioUsecase, usuarioRepository, transactionGateway);
    }

    @Bean
    public InativarUsuarioUsecase inativarUsuarioUsecase(
            BuscarUsuarioUsecase buscarUsuarioUsecase,
            UsuarioRepository usuarioRepository,
            TransactionGateway transactionGateway) {
        return new InativarUsuarioUsecaseImpl(buscarUsuarioUsecase, usuarioRepository, transactionGateway);
    }
}