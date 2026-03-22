package com.github.lilianjaf.mestremenuclean.usuario.core.usecase;

import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Cliente;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.Endereco;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoNativo;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.TipoUsuario;
import com.github.lilianjaf.mestremenuclean.usuario.core.domain.UsuarioBase;
import com.github.lilianjaf.mestremenuclean.usuario.core.exception.DomainException;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.TransactionGateway;
import com.github.lilianjaf.mestremenuclean.usuario.core.gateway.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InativarUsuarioUsecaseTest {

    @Mock
    private BuscarUsuarioUsecase buscarUsuarioUsecase;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private TransactionGateway transactionGateway;

    @InjectMocks
    private InativarUsuarioUsecaseImpl inativarUsuarioUsecase;

    private UsuarioBase usuarioMock;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        TipoUsuario tipo = new TipoUsuario("Cliente Padrão", TipoNativo.CLIENTE);
        Endereco endereco = new Endereco("Rua A", "123", null, "Bairro", "Cidade", "12345-678", "SP");

        usuarioMock = new Cliente(usuarioId, "Lilian", "lilian@email.com", "lilian.login", "senha123", tipo, endereco, LocalDateTime.now(), true);
    }

    @Test
    void deveInativarUsuarioComSucesso() {
        when(buscarUsuarioUsecase.buscarEntidade(usuarioId)).thenReturn(usuarioMock);
        doAnswer(invocation -> {
            Supplier<?> supplier = invocation.getArgument(0);
            return supplier.get();
        }).when(transactionGateway).execute(any(Supplier.class));

        inativarUsuarioUsecase.inativar(usuarioId);

        assertFalse(usuarioMock.getAtivo(), "O usuário deveria estar inativo (false)");

        verify(repository, times(1)).salvar(usuarioMock);
    }

    @Test
    void deveLancarExcecaoSeUsuarioJaEstiverInativo() {
        usuarioMock.desativar();
        when(buscarUsuarioUsecase.buscarEntidade(usuarioId)).thenReturn(usuarioMock);
        doAnswer(invocation -> {
            Supplier<?> supplier = invocation.getArgument(0);
            return supplier.get();
        }).when(transactionGateway).execute(any(Supplier.class));

        DomainException exception = assertThrows(DomainException.class, () -> {
            inativarUsuarioUsecase.inativar(usuarioId);
        });

        assertEquals("O usuário já está inativo.", exception.getMessage());

        verify(repository, never()).salvar(any());
    }
}