package com.luizalabs.communication.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizalabs.communication.dto.AgendamentoRequestDTO;
import com.luizalabs.communication.dto.DestinatarioDTO;
import com.luizalabs.communication.dto.MensagemDTO;
import com.luizalabs.communication.enums.TipoComunicacaoEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AgendamentoIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("comunicacao_db")
            .withUsername("comunicacao")
            .withPassword("comunicacao123");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarBuscarEDeletarAgendamentoComSucesso() throws Exception {
        AgendamentoRequestDTO request = new AgendamentoRequestDTO(
                LocalDateTime.now().plusHours(2),
                new MensagemDTO("Teste de integração"),
                new ArrayList<>(
                        java.util.List.of(
                                new DestinatarioDTO("teste@magalu.com", TipoComunicacaoEnum.EMAIL)
                        )
                )
        );

        String response = mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.mensagem.conteudo").value("Teste de integração"))
                .andExpect(jsonPath("$.data.envio.status").value("PENDENTE"))
                .andExpect(jsonPath("$.data.envio.enviadoEm").doesNotExist())
                .andExpect(jsonPath("$.data.envio.erroEnvio").doesNotExist())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).path("data").path("id").asLong();

        mockMvc.perform(get("/agendamentos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.envio.status").value("PENDENTE"));

        mockMvc.perform(delete("/agendamentos/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/agendamentos/{id}", id))
                .andExpect(status().isNotFound());
    }
}
