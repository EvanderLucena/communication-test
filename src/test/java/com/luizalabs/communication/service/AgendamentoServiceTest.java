package com.luizalabs.communication.service;

import com.luizalabs.communication.dto.AgendamentoRequestDTO;
import com.luizalabs.communication.dto.AgendamentoResponseDTO;
import com.luizalabs.communication.dto.DestinatarioDTO;
import com.luizalabs.communication.dto.MensagemDTO;
import com.luizalabs.communication.enums.TipoComunicacaoEnum;
import com.luizalabs.communication.mapper.AgendamentoMapper;
import com.luizalabs.communication.model.Agendamento;
import com.luizalabs.communication.model.Destinatario;
import com.luizalabs.communication.model.Mensagem;
import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import com.luizalabs.communication.repository.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private AgendamentoMapper mapper;

    @InjectMocks
    private AgendamentoService service;

    private Agendamento agendamento;
    private AgendamentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        agendamento = Agendamento.builder()
                .id(1L)
                .dataHoraEnvio(LocalDateTime.now().plusDays(1))
                .status(StatusComunicacaoEnum.PENDENTE)
                .criadoEm(LocalDateTime.now())
                .mensagem(Mensagem.builder().conteudo("Teste").build())
                .destinatarios(Collections.emptyList())
                .build();

        responseDTO = new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getDataHoraEnvio(),
                agendamento.getStatus(),
                null,
                null,
                agendamento.getCriadoEm(),
                new MensagemDTO("Teste"),
                Collections.emptyList()
        );
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(mapper.toDTO(agendamento)).thenReturn(responseDTO);

        var result = service.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(StatusComunicacaoEnum.PENDENTE, result.get().status());
        verify(repository).findById(1L);
    }

    @Test
    void deveRetornarVazioSeIdNaoExistir() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        var result = service.buscarPorId(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    void deveDeletarComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));
        doNothing().when(repository).delete(agendamento);

        boolean deleted = service.deletar(1L);

        assertTrue(deleted);
        verify(repository).delete(agendamento);
    }

    @Test
    void naoDeveDeletarSeIdNaoExistir() {
        when(repository.existsById(2L)).thenReturn(false);

        boolean deleted = service.deletar(2L);

        assertFalse(deleted);
        verify(repository, never()).deleteById(2L);
    }

    @Test
    void deveFiltrarPorStatus() {
        when(repository.findByStatus(StatusComunicacaoEnum.PENDENTE))
                .thenReturn(Collections.singletonList(agendamento));
        when(mapper.toDTO(agendamento)).thenReturn(responseDTO);

        var lista = service.listar(StatusComunicacaoEnum.PENDENTE);

        assertEquals(1, lista.size());
        assertEquals(StatusComunicacaoEnum.PENDENTE, lista.get(0).status());
    }

    @Test
    void deveRetornarTodosSeStatusForNulo() {
        when(repository.findAll()).thenReturn(Collections.singletonList(agendamento));
        when(mapper.toDTO(agendamento)).thenReturn(responseDTO);

        var lista = service.listar(null);

        assertEquals(1, lista.size());
        assertEquals(agendamento.getId(), lista.get(0).id());
    }

    @Test
    void deveCriarAgendamentoComSucesso() {
        AgendamentoRequestDTO dto = new AgendamentoRequestDTO(
                LocalDateTime.now().plusHours(1),
                new MensagemDTO("Mensagem de teste"),
                List.of(new DestinatarioDTO("teste@magalu.com", TipoComunicacaoEnum.EMAIL))
        );

        Agendamento entity = Agendamento.builder()
                .id(1L)
                .dataHoraEnvio(dto.dataHoraEnvio())
                .status(StatusComunicacaoEnum.PENDENTE)
                .mensagem(Mensagem.builder().conteudo(dto.mensagem().conteudo()).build())
                .destinatarios(List.of(Destinatario.builder()
                        .contato("teste@magalu.com")
                        .tipo(TipoComunicacaoEnum.EMAIL)
                        .build()))
                .criadoEm(LocalDateTime.now())
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(new AgendamentoResponseDTO(
                entity.getId(),
                entity.getDataHoraEnvio(),
                entity.getStatus(),
                null,
                null,
                entity.getCriadoEm(),
                new MensagemDTO("Mensagem de teste"),
                List.of(new DestinatarioDTO("teste@magalu.com", TipoComunicacaoEnum.EMAIL))
        ));

        var response = service.criarAgendamento(dto);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals(StatusComunicacaoEnum.PENDENTE, response.status());
        verify(repository).save(entity);
    }
}
