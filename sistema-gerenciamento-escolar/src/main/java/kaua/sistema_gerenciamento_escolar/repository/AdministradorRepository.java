package kaua.sistema_gerenciamento_escolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kaua.sistema_gerenciamento_escolar.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer>{

    Administrador findByEmail(String email);
    
}
