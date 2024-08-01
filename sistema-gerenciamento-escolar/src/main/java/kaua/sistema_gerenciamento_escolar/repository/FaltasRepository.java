package kaua.sistema_gerenciamento_escolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kaua.sistema_gerenciamento_escolar.model.Faltas;

@Repository
public interface FaltasRepository extends JpaRepository<Faltas, Integer>{
    List<Faltas> findByAlunoId(Integer aluno_id);

    List<Faltas> findByAlunoIdAndMateriaId(Integer aluno_id, Integer materia_id);
}
