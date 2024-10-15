package org.serratec.curriculos.repository;

import java.util.List;
import org.serratec.curriculos.model.Candidato;
import org.serratec.curriculos.model.Escolaridade;
import org.serratec.curriculos.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    List<Candidato> findByVagaDesejada(Vaga vagaDesejada);

    List<Candidato> findByEscolaridade(Escolaridade escolaridade);
}
