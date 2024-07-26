package kaua.sistema_gerenciamento_escolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kaua.sistema_gerenciamento_escolar.model.Materias;

public interface MateriasRepository extends JpaRepository<Materias, Integer> {
    
}
