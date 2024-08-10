package kaua.sistema_gerenciamento_escolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import kaua.sistema_gerenciamento_escolar.model.Materias;

public interface MateriasRepository extends JpaRepository<Materias, Integer> {
    
    Optional<Materias> findByNome(String nomeMateria);

    @Query("SELECT m From Materias m JOIN m.alunos a WHERE a.id = :alunoId")
    List<Materias> findMateriaByAlunoId(@Param("alunoId") int aluno_id);

    List<Materias> findByProfessorId(Integer professor_id);
}
