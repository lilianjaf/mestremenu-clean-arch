package com.github.lilianjaf.mestremenuclean.restaurante.core.usecase;

import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Restaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.domain.Usuario;
import com.github.lilianjaf.mestremenuclean.restaurante.core.dto.DadosCriacaoRestaurante;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.RestauranteRepository;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.gateway.UsuarioGateway;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.CriacaoRestauranteContext;
import com.github.lilianjaf.mestremenuclean.restaurante.core.rules.ValidadorCriacaoRestauranteRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.UsuarioLogadoNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.ObterUsuarioLogadoGateway;

import java.util.List;

public class CriarRestauranteUseCaseImpl implements CriarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioGateway usuarioGateway;
    private final ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;
    private final TransactionGateway transactionGateway;
    private final List<ValidadorCriacaoRestauranteRule> permissaoRules;
    private final List<ValidadorCriacaoRestauranteRule> rules;

    public CriarRestauranteUseCaseImpl(RestauranteRepository restauranteRepository,
                                       UsuarioGateway usuarioGateway,
                                       ObterUsuarioLogadoGateway obterUsuarioLogadoGateway,
                                       TransactionGateway transactionGateway,
                                       List<ValidadorCriacaoRestauranteRule> permissaoRules,
                                       List<ValidadorCriacaoRestauranteRule> rules) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioGateway = usuarioGateway;
        this.obterUsuarioLogadoGateway = obterUsuarioLogadoGateway;
        this.transactionGateway = transactionGateway;
        this.permissaoRules = permissaoRules;
        this.rules = rules;
    }

    @Override
    public Restaurante executar(DadosCriacaoRestaurante dados) {
        UsuarioBase usuarioLogado = obterUsuarioLogadoGateway.obterUsuarioLogado()
                .orElseThrow(() -> new UsuarioLogadoNaoEncontradoException("Usuário logado não encontrado"));

        Usuario dono = usuarioGateway.buscarPorId(dados.idDono()).orElse(null);

        CriacaoRestauranteContext context = new CriacaoRestauranteContext(usuarioLogado, dono, dados);

        return transactionGateway.execute(() -> {
            permissaoRules.forEach(rule -> rule.validar(context));
            rules.forEach(rule -> rule.validar(context));

            Restaurante restaurante = new Restaurante(
                    dados.nome(),
                    dados.endereco(),
                    dados.tipoCozinha(),
                    dados.horarioFuncionamento(),
                    dados.idDono()
            );

            return restauranteRepository.salvar(restaurante);
        });
    }
}
