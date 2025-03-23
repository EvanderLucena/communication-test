package com.luizalabs.communication.controller;

import com.luizalabs.communication.dto.AgendamentoRequestDTO;
import com.luizalabs.communication.dto.AgendamentoResponseDTO;
import com.luizalabs.communication.dto.ApiResponseDTO;
import com.luizalabs.communication.enums.StatusComunicacaoEnum;
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
    public ResponseEntity<ApiResponseDTO<AgendamentoResponseDTO>> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = service.criarAgendamento(dto);
        URI location = URI.create("/agendamentos/" + response.id());
        return ResponseEntity.created(location)
                .body(ApiResponseDTO.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AgendamentoResponseDTO>> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(dto -> ResponseEntity.ok(ApiResponseDTO.success(dto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.deletar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<ApiResponseDTO<String>> consultarStatus(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(dto -> ResponseEntity.ok(ApiResponseDTO.success(dto.status().name())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<AgendamentoResponseDTO>>> listar(@RequestParam(required = false) StatusComunicacaoEnum status) {
        return ResponseEntity.ok(ApiResponseDTO.success(service.listar(status)));
    }
}