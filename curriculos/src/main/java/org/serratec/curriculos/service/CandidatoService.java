package org.serratec.curriculos.service;


import java.util.List;
import org.serratec.curriculos.dto.CandidatoDto;
import org.serratec.curriculos.model.Candidato;
import org.serratec.curriculos.model.Escolaridade;
import org.serratec.curriculos.model.StatusCurriculo;
import org.serratec.curriculos.model.Vaga;
import org.serratec.curriculos.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    public Candidato adicionarCandidato(CandidatoDto candidatoDTO) {
        Candidato candidato = new Candidato();
        candidato.setNome(candidatoDTO.nome());
        candidato.setCpf(candidatoDTO.cpf());
        candidato.setDataNascimento(candidatoDTO.dataNascimento());
        candidato.setEscolaridade(Escolaridade.valueOf(candidatoDTO.escolaridade().toUpperCase()));
        candidato.setVagaDesejada(Vaga.valueOf(candidatoDTO.vagaDesejada().toUpperCase()));
        candidato.setStatusCurriculo(StatusCurriculo.EM_ANALISE);
        
        return candidatoRepository.save(candidato);
    }
    
    public List<Candidato> listarTodosCandidatos() {
        return candidatoRepository.findAll();
    }

    public List<Candidato> buscarPorVaga(Vaga vaga) {
        return candidatoRepository.findByVagaDesejada(vaga);
    }

    public List<Candidato> buscarPorEscolaridade(Escolaridade escolaridade) {
        return candidatoRepository.findByEscolaridade(escolaridade);
    }

    public void atualizarStatus(Long id, StatusCurriculo status) {
        Candidato candidato = candidatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidato com ID " + id + " não encontrado"));
        
        candidato.setStatusCurriculo(status);
        candidatoRepository.save(candidato);
    }

    public Candidato atualizarCandidato(Long id, CandidatoDto candidatoDTO) {
        Candidato candidato = candidatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidato com ID " + id + " não encontrado"));

        candidato.setNome(candidatoDTO.nome());
        candidato.setCpf(candidatoDTO.cpf());
        candidato.setDataNascimento(candidatoDTO.dataNascimento());
        
        try {
            candidato.setEscolaridade(Escolaridade.valueOf(candidatoDTO.escolaridade().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Escolaridade inválida: " + candidatoDTO.escolaridade());
        }

        candidato.setVagaDesejada(Vaga.valueOf(candidatoDTO.vagaDesejada().toUpperCase()));

        return candidatoRepository.save(candidato);
    }

    public void deletarCandidato(Long id) {
        Candidato candidato = candidatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidato com ID " + id + " não encontrado"));
        
        candidatoRepository.delete(candidato);
    }
}