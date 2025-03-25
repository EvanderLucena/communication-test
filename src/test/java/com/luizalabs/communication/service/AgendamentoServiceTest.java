package com.luizalabs.communication.service;

import com.luizalabs.communication.dto.*;
import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import com.luizalabs.communication.enums.TipoComunicacaoEnum;
import com.luizalabs.communication.mapper.AgendamentoMapper;
import com.luizalabs.communication.model.Agendamento;
import com.luizalabs.communication.model.Destinatario;
import com.luizalabs.communication.model.Envio;
import com.luizalabs.communication.model.Mensagem;
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
                .criadoEm(LocalDateTime.now())
                .mensagem(Mensagem.builder().conteudo("Teste").build())
                .destinatarios(Collections.emptyList())
                .build();

        responseDTO = new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getDataHoraEnvio(),
                agendamento.getCriadoEm(),
                new MensagemDTO("Teste"),
                Collections.emptyList(),
                new EnvioDTO(StatusComunicacaoEnum.PENDENTE, null, null)
        );
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(mapper.toDTO(agendamento)).thenReturn(responseDTO);

        var result = service.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(StatusComunicacaoEnum.PENDENTE, result.get().envio().status());
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
        when(repository.findByEnvio_Status(StatusComunicacaoEnum.PENDENTE))
                .thenReturn(Collections.singletonList(agendamento));
        when(mapper.toDTO(agendamento)).thenReturn(responseDTO);

        var lista = service.listar(StatusComunicacaoEnum.PENDENTE);

        assertEquals(1, lista.size());
        assertEquals(StatusComunicacaoEnum.PENDENTE, lista.get(0).envio().status());
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
        LocalDateTime dataHoraEnvio = LocalDateTime.now().plusHours(1);
        LocalDateTime criadoEm = LocalDateTime.now();

        AgendamentoRequestDTO dto = new AgendamentoRequestDTO(
                dataHoraEnvio,
                new MensagemDTO("Mensagem de teste"),
                List.of(new DestinatarioDTO("teste@magalu.com", TipoComunicacaoEnum.EMAIL))
        );

        Agendamento salvo = Agendamento.builder()
                .id(1L)
                .dataHoraEnvio(dataHoraEnvio)
                .criadoEm(criadoEm)
                .mensagem(Mensagem.builder().conteudo("Mensagem de teste").build())
                .destinatarios(List.of(Destinatario.builder()
                        .contato("teste@magalu.com")
                        .tipo(TipoComunicacaoEnum.EMAIL)
                        .build()))
                .envio(Envio.builder()
                        .status(StatusComunicacaoEnum.PENDENTE)
                        .build())
                .build();

        when(repository.save(any(Agendamento.class))).thenReturn(salvo);
        when(mapper.toDTO(salvo)).thenReturn(new AgendamentoResponseDTO(
                salvo.getId(),
                salvo.getDataHoraEnvio(),
                salvo.getCriadoEm(),
                new MensagemDTO("Mensagem de teste"),
                List.of(new DestinatarioDTO("teste@magalu.com", TipoComunicacaoEnum.EMAIL)),
                new EnvioDTO(StatusComunicacaoEnum.PENDENTE, null, null)
        ));

        AgendamentoResponseDTO response = service.criarAgendamento(dto);

        assertNotNull(response, "A resposta não deveria ser nula");
        assertEquals(1L, response.id());
        assertEquals(StatusComunicacaoEnum.PENDENTE, response.envio().status());

        verify(repository).save(any(Agendamento.class));
    }
}
