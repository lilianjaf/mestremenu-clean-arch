package com.github.lilianjaf.mestremenuclean.cardapio.infra.gateway;

import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.cardapio.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.cardapio.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component("cardapioUsuarioGatewayImpl")
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioRepository usuarioRepository;

    public UsuarioGatewayImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioRepository.findById(id).map(u -> {
            TipoNativo tipo = null;
            if (u.getTipoCustomizado() != null && u.getTipoCustomizado().getTipoNativo() != null) {
                try {
                    tipo = TipoNativo.valueOf(u.getTipoCustomizado().getTipoNativo().name());
                } catch (IllegalArgumentException e) {
                    // Ignore unknown types
                }
            }
            return new Usuario(u.getId(), tipo);
        });
    }

    @Override
    public Optional<Usuario> buscarPorUsuario(String username) {
        return usuarioRepository.findByLogin(username).map(u -> {
            TipoNativo tipo = null;
            if (u.getTipoCustomizado() != null && u.getTipoCustomizado().getTipoNativo() != null) {
                try {
                    tipo = TipoNativo.valueOf(u.getTipoCustomizado().getTipoNativo().name());
                } catch (IllegalArgumentException e) {
                    // Ignore unknown types
                }
            }
            return new Usuario(u.getId(), tipo);
        });
    }
}
