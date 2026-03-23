package com.github.lilianjaf.mestremenuclean.restaurante.infra.gateway;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioRepository usuarioRepository;

    public UsuarioGatewayImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioRepository.findById(id).map(usuarioBase -> {
            TipoNativo tipoNativoRestaurante = null;
            if (usuarioBase.getTipoCustomizado() != null && usuarioBase.getTipoCustomizado().getTipoNativo() != null) {
                try {
                    tipoNativoRestaurante = TipoNativo.valueOf(usuarioBase.getTipoCustomizado().getTipoNativo().name());
                } catch (IllegalArgumentException e) {
                    // Ignore or handle unknown types
                }
            }
            return new Usuario(usuarioBase.getId(), tipoNativoRestaurante);
        });
    }
}
