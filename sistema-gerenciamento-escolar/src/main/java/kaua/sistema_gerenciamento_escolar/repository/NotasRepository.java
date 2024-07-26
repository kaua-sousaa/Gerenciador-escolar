package kaua.sistema_gerenciamento_escolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kaua.sistema_gerenciamento_escolar.model.Notas;

public interface NotasRepository extends JpaRepository<Notas, Integer> {
    
}
