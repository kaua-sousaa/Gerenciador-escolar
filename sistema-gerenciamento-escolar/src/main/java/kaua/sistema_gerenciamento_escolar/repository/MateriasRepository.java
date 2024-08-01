package kaua.sistema_gerenciamento_escolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kaua.sistema_gerenciamento_escolar.model.Materias;

public interface MateriasRepository extends JpaRepository<Materias, Integer> {
    
    Optional<Materias> findByNomeAndProfessorId(String nomeMateria, Integer professorId);
}
