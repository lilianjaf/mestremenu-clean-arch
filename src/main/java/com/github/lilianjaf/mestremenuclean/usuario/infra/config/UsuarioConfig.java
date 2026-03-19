package com.github.lilianjaf.mestremenuclean.usuario.infra.config;

import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.CodificadorDeSenha;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
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
            CodificadorDeSenha codificadorDeSenha) {
        return new CriarUsuarioUsecase(usuarioRepository, tipoUsuarioRepository, codificadorDeSenha);
    }

    @Bean
    public BuscarUsuarioUsecase buscarUsuarioUsecase(UsuarioRepository usuarioRepository) {
        return new BuscarUsuarioUsecase(usuarioRepository);
    }

    @Bean
    public AtualizarUsuarioUsecase atualizarUsuarioUsecase(
            BuscarUsuarioUsecase buscarUsuarioUsecase,
            UsuarioRepository usuarioRepository) {
        return new AtualizarUsuarioUsecase(buscarUsuarioUsecase, usuarioRepository);
    }

    @Bean
    public InativarUsuarioUsecase inativarUsuarioUsecase(
            BuscarUsuarioUsecase buscarUsuarioUsecase,
            UsuarioRepository usuarioRepository) {
        return new InativarUsuarioUsecase(buscarUsuarioUsecase, usuarioRepository);
    }
}