package kaua.sistema_gerenciamento_escolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kaua.sistema_gerenciamento_escolar.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

}
