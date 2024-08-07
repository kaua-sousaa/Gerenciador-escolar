package kaua.sistema_gerenciamento_escolar.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.model.Materias;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    @Query("SELECT a.id FROM Materias m join m.alunos a WHERE m.id =:materiaId")
    Set<Integer> findAlunoIdsByMateriaId(@Param("materiaId") Integer materiaId);

    List<Aluno> findAlunosByMateriasMatriculadas(Materias materia);
}
