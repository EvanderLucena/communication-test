package com.luizalabs.communication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizalabs.communication.dto.*;
import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import com.luizalabs.communication.enums.TipoComunicacaoEnum;
import com.luizalabs.communication.service.AgendamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AgendamentoController.class)
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgendamentoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private AgendamentoResponseDTO agendamentoResponseDTO;

    @BeforeEach
    void setUp() {
        EnvioDTO envio = new EnvioDTO(StatusComunicacaoEnum.PENDENTE, null, null);
        agendamentoResponseDTO = new AgendamentoResponseDTO(
                1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now(),
                new MensagemDTO("Teste"),
                List.of(new DestinatarioDTO("email@magalu.com", TipoComunicacaoEnum.EMAIL)),
                envio
        );
    }

    @Test
    void deveCriarAgendamentoComSucesso() throws Exception {
        AgendamentoRequestDTO requestDTO = new AgendamentoRequestDTO(
                agendamentoResponseDTO.dataHoraEnvio(),
                new MensagemDTO("Teste"),
                List.of(new DestinatarioDTO("email@magalu.com", TipoComunicacaoEnum.EMAIL))
        );

        when(service.criarAgendamento(any())).thenReturn(agendamentoResponseDTO);

        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void deveBuscarAgendamentoPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(Optional.of(agendamentoResponseDTO));

        mockMvc.perform(get("/agendamentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void deveRetornarNotFoundAoBuscarAgendamentoInexistente() throws Exception {
        when(service.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/agendamentos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveConsultarStatusAgendamento() throws Exception {
        Long id = 1L;

        when(service.buscarStatusPorId(id)).thenReturn(Optional.of("PENDENTE"));

        mockMvc.perform(get("/agendamentos/{id}/status", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value("PENDENTE"));

        verify(service).buscarStatusPorId(id);
    }

    @Test
    void deveDeletarAgendamentoComSucesso() throws Exception {
        when(service.deletar(1L)).thenReturn(true);

        mockMvc.perform(delete("/agendamentos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoDeletarInexistente() throws Exception {
        when(service.deletar(99L)).thenReturn(false);

        mockMvc.perform(delete("/agendamentos/99"))
                .andExpect(status().isNotFound());
    }
}
