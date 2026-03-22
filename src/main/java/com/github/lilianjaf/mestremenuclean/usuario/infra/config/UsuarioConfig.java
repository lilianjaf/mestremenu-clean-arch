package com.github.lilianjaf.mestremenuclean.usuario.infra.config;

import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.CodificadorDeSenha;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.AtualizarUsuarioUsecase;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.BuscarUsuarioUsecase;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.CriarUsuarioUsecase;
import com.github.lilianjaf.mestremenuclean.usuario.core.usecase.InativarUsuarioUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public CriarUsuarioUsecase criarUsuarioUsecase(
            UsuarioRepository usuarioRepository,
            TipoUsuarioRepository tipoUsuarioRepository,
            CodificadorDeSenha codificadorDeSenha,
            TransactionGateway transactionGateway) {
        return new CriarUsuarioUsecase(usuarioRepository, tipoUsuarioRepository, codificadorDeSenha, transactionGateway);
    }

    @Bean
    public BuscarUsuarioUsecase buscarUsuarioUsecase(UsuarioRepository usuarioRepository) {
        return new BuscarUsuarioUsecase(usuarioRepository);
    }

    @Bean
    public AtualizarUsuarioUsecase atualizarUsuarioUsecase(
            BuscarUsuarioUsecase buscarUsuarioUsecase,
            UsuarioRepository usuarioRepository,
            TransactionGateway transactionGateway) {
        return new AtualizarUsuarioUsecase(buscarUsuarioUsecase, usuarioRepository, transactionGateway);
    }

    @Bean
    public InativarUsuarioUsecase inativarUsuarioUsecase(
            BuscarUsuarioUsecase buscarUsuarioUsecase,
            UsuarioRepository usuarioRepository,
            TransactionGateway transactionGateway) {
        return new InativarUsuarioUsecase(buscarUsuarioUsecase, usuarioRepository, transactionGateway);
    }
}