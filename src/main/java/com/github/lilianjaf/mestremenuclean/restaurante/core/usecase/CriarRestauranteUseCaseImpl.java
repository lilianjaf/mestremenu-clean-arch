package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.restaurante.core.dto.DadosCriacaoRestaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.CriacaoRestauranteContext;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ValidadorCriacaoRestauranteRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;

import java.util.List;

public class CriarRestauranteUseCaseImpl implements CriarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioGateway usuarioGateway;
    private final List<ValidadorCriacaoRestauranteRule> regrasValidacao;

    public CriarRestauranteUseCaseImpl(RestauranteRepository restauranteRepository, UsuarioGateway usuarioGateway, List<ValidadorCriacaoRestauranteRule> regrasValidacao) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioGateway = usuarioGateway;
        this.regrasValidacao = regrasValidacao;
    }

    @Override
    public Restaurante executar(DadosCriacaoRestaurante dados) {
        Usuario dono = usuarioGateway.buscarPorId(dados.idDono())
                .orElseThrow(() -> new DomainException("Dono não encontrado com o ID fornecido."));

        CriacaoRestauranteContext context = new CriacaoRestauranteContext(dono, dados);
        regrasValidacao.forEach(regra -> regra.validar(context));

        Restaurante restaurante = new Restaurante(
                dados.nome(),
                dados.endereco(),
                dados.tipoCozinha(),
                dados.horarioFuncionamento(),
                dados.idDono()
        );

        return restauranteRepository.salvar(restaurante);
    }
}
