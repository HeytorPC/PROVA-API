package org.serratec.curriculos.dto;

import org.serratec.curriculos.dto.CandidatoDto;
import java.time.LocalDate;

public record CandidatoDto(
	    String nome,
	    String cpf,
	    LocalDate dataNascimento,
	    String escolaridade,
	    String vagaDesejada
	) {}