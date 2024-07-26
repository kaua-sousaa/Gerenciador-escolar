package kaua.sistema_gerenciamento_escolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kaua.sistema_gerenciamento_escolar.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    
}
