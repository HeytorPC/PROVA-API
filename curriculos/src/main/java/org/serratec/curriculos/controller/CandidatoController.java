package org.serratec.curriculos.controller;

import java.util.List;
import org.serratec.curriculos.dto.CandidatoDto;
import org.serratec.curriculos.model.Candidato;
import org.serratec.curriculos.model.Escolaridade;
import org.serratec.curriculos.model.StatusCurriculo;
import org.serratec.curriculos.model.Vaga;
import org.serratec.curriculos.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @PostMapping
    public Candidato adicionarCandidato(@RequestBody CandidatoDto candidatoDTO) {
        return candidatoService.adicionarCandidato(candidatoDTO);
    }

    @GetMapping
    public List<Candidato> listarCandidatos() {
        return candidatoService.listarTodosCandidatos();
    }

    @GetMapping("/vaga/{vaga}")
    public List<Candidato> buscarPorVaga(@PathVariable String vaga) {
        return candidatoService.buscarPorVaga(Vaga.valueOf(vaga.toUpperCase()));
    }

    @GetMapping("/escolaridade/{escolaridade}")
    public List<Candidato> buscarPorEscolaridade(@PathVariable String escolaridade) {
        return candidatoService.buscarPorEscolaridade(Escolaridade.valueOf(escolaridade.toUpperCase()));
    }

    @PutMapping("/{id}/status/{status}")
    public void atualizarStatus(@PathVariable Long id, @PathVariable String status) {
        try {
            StatusCurriculo statusCurriculo = StatusCurriculo.valueOf(status.toUpperCase());
            candidatoService.atualizarStatus(id, statusCurriculo);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status inválido: " + status);
        }
    }

    @PutMapping("/{id}")
    public Candidato atualizarCandidato(@PathVariable Long id, @RequestBody CandidatoDto candidatoDTO) {
        try {
            return candidatoService.atualizarCandidato(id, candidatoDTO);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deletarCandidato(@PathVariable Long id) {
        try {
            candidatoService.deletarCandidato(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
