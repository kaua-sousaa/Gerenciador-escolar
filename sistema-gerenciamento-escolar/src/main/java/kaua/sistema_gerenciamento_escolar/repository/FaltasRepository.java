package kaua.sistema_gerenciamento_escolar.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kaua.sistema_gerenciamento_escolar.model.Faltas;
import kaua.sistema_gerenciamento_escolar.model.Materias;

@Repository
public interface FaltasRepository extends JpaRepository<Faltas, Integer>{
    List<Faltas> findByAlunoId(Integer aluno_id);

    List<Faltas> findByAlunoIdAndMateriaId(Integer aluno_id, Integer materia_id);

    Set<Faltas> findByMateriaIn(List<Materias> materia);
    void deleteByAlunoId(Integer aluno_id);

    List<Faltas> findByMateriaId(Integer materia_id);
}
