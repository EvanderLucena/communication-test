package com.luizalabs.communication.controller;

import com.luizalabs.communication.dto.AgendamentoRequestDTO;
import com.luizalabs.communication.dto.AgendamentoResponseDTO;
import com.luizalabs.communication.dto.ApiResponse;
import com.luizalabs.communication.model.StatusComunicacao;
import com.luizalabs.communication.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;

    @PostMapping
    public ResponseEntity<ApiResponse<AgendamentoResponseDTO>> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = service.criarAgendamento(dto);
        URI location = URI.create("/agendamentos/" + response.id());
        return ResponseEntity.created(location)
                .body(ApiResponse.success(response, "Agendamento criado com sucesso"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AgendamentoResponseDTO>> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(dto -> ResponseEntity.ok(ApiResponse.success(dto, "Agendamento encontrado")))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "Agendamento não encontrado")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.deletar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<ApiResponse<String>> consultarStatus(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(agendamento -> ResponseEntity.ok(ApiResponse.success(agendamento.status().name(), "Status do agendamento")))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "Agendamento não encontrado")));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AgendamentoResponseDTO>>> listar(@RequestParam(required = false) StatusComunicacao status) {
        List<AgendamentoResponseDTO> lista = service.listar(status);
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de agendamentos"));
    }
}
