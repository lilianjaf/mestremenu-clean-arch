package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.AtualizarRestauranteRuleContextDto;
import com.github.lilianjaf.mestremenuclean.restaurante.core.dto.DadosAtualizacaoRestaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.AtualizarRestauranteRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioLogadoNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.ObterUsuarioLogadoGateway;

import java.util.List;
import java.util.UUID;

public class AtualizarRestauranteUseCaseImpl implements AtualizarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;
    private final List<AtualizarRestauranteRule> permissaoRules;
    private final List<AtualizarRestauranteRule> rules;

    public AtualizarRestauranteUseCaseImpl(RestauranteRepository restauranteRepository,
                                           ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                           List<AtualizarRestauranteRule> permissaoRules,
                                           List<AtualizarRestauranteRule> rules) {
        this.restauranteRepository = restauranteRepository;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
        this.permissaoRules = permissaoRules;
        this.rules = rules;
    }

    @Override
    public Restaurante executar(UUID id, DadosAtualizacaoRestaurante dados) {
        UsuarioBase usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new UsuarioLogadoNaoEncontradoException("Usuário logado não encontrado"));

        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new DomainException("Restaurante não encontrado."));

        AtualizarRestauranteRuleContextDto context = new AtualizarRestauranteRuleContextDto(usuarioLogado, restaurante);

        permissaoRules.forEach(rule -> rule.validar(context));
        rules.forEach(rule -> rule.validar(context));

        restaurante.atualizar(
                dados.nome(),
                dados.endereco(),
                dados.tipoCozinha(),
                dados.horarioFuncionamento()
        );

        return restauranteRepository.salvar(restaurante);
    }
}
