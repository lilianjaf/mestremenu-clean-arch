package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.ObterUsuarioLogadoGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TipoUsuarioRepository;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.AtualizacaoTipoUsuarioContext;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorAtualizacaoTipoUsuarioRule;
import com.github.lilianjaf.mestremenuclean.usuario.core.rules.ValidadorPermissaoRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarTipoUsuarioUsecaseImplTest {

    @Mock
    private TipoUsuarioRepository repository;

    @Mock
    private TransactionGateway transactionGateway;

    @Mock
    private ObterUsuarioLogadoGateway obterUsuarioLogadoGateway;

    @Mock
    private ValidadorAtualizacaoTipoUsuarioRule rule;

    @Mock
    private ValidadorPermissaoRule permissaoRule;

    private AtualizarTipoUsuarioUsecaseImpl usecase;

    @BeforeEach
    void setUp() {
        usecase = new AtualizarTipoUsuarioUsecaseImpl(
                repository,
                transactionGateway,
                List.of(rule),
                List.of(permissaoRule),
                obterUsuarioLogadoGateway
        );
    }

    @Test
    @DisplayName("Deve atualizar corretamente um tipo de usuário")
    void deveAtualizarCorretamenteUmTipoDeUsuario() {
        UUID id = UUID.randomUUID();
        String novoNome = "Novo Nome";
        TipoUsuario tipoExistente = new TipoUsuario(id, "Antigo Nome", TipoNativo.CLIENTE);
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(repository.findById(id)).thenReturn(Optional.of(tipoExistente));
        when(repository.findByNome(novoNome)).thenReturn(Optional.empty());
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(transactionGateway).execute(any(Runnable.class));

        usecase.atualizar(id, novoNome);

        verify(permissaoRule).validar(usuarioLogado);
        verify(rule).validar(any(AtualizacaoTipoUsuarioContext.class));
        verify(repository).salvar(tipoExistente);
        assertEquals(novoNome, tipoExistente.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tipo de usuário não for encontrado")
    void deveLancarExcecaoQuandoTipoUsuarioNaoEncontrado() {
        UUID id = UUID.randomUUID();
        String novoNome = "Novo Nome";
        UsuarioBase usuarioLogado = mock(UsuarioBase.class);

        when(obterUsuarioLogadoGateway.obterUsuarioLogado()).thenReturn(Optional.of(usuarioLogado));
        when(repository.findById(id)).thenReturn(Optional.empty());
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(transactionGateway).execute(any(Runnable.class));

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> usecase.atualizar(id, novoNome));

        verify(permissaoRule).validar(usuarioLogado);
        verify(repository, never()).salvar(any());
    }
}
