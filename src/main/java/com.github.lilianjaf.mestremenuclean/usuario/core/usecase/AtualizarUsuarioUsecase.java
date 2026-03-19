package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Endereco;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;

import java.util.UUID;

public class AtualizarUsuarioUsecase {
    private final BuscarUsuarioUsecase buscarUsuarioUsecase;
    private final UsuarioRepository repository;

    public AtualizarUsuarioUsecase(BuscarUsuarioUsecase buscarUsuarioUsecase, UsuarioRepository repository) {
        this.buscarUsuarioUsecase = buscarUsuarioUsecase;
        this.repository = repository;
    }

    public void atualizarComEndereco(
            UUID id, String novoNome, String novoEmail,
            String logradouro, String numero, String complemento, String bairro, String cidade, String cep, String uf) {

        UsuarioBase usuario = buscarUsuarioUsecase.buscarEntidade(id);

        usuario.atualizarDadosBasicos(novoNome, novoEmail);

        Endereco novoEndereco = new Endereco(logradouro, numero, complemento, bairro, cidade, cep, uf);
        usuario.atualizarEndereco(novoEndereco);

        repository.salvar(usuario);
    }

    public void atualizarSemEndereco(UUID id, String novoNome, String novoEmail) {
        UsuarioBase usuario = buscarUsuarioUsecase.buscarEntidade(id);
        usuario.atualizarDadosBasicos(novoNome, novoEmail);
        repository.salvar(usuario);
    }
}