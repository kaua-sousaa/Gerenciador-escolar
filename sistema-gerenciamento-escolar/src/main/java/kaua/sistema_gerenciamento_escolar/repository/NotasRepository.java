package kaua.sistema_gerenciamento_escolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import kaua.sistema_gerenciamento_escolar.model.Notas;

public interface NotasRepository extends JpaRepository<Notas, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Notas n WHERE n.alunos.id =:alunoId")
    void deleteByAlunoId(@Param("alunoId") Integer alunoId);

}
