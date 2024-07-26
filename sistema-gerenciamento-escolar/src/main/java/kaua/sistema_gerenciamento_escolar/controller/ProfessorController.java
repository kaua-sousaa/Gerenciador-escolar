package kaua.sistema_gerenciamento_escolar.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kaua.sistema_gerenciamento_escolar.dto.NotasDTO;
import kaua.sistema_gerenciamento_escolar.dto.ProfessorDTO;
import kaua.sistema_gerenciamento_escolar.model.Professor;
import kaua.sistema_gerenciamento_escolar.repository.ProfessorRepository;
import kaua.sistema_gerenciamento_escolar.service.ProfessorService;

@RestController
public class ProfessorController {
    
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/professor")
    public ResponseEntity<List<Professor>> getProfessor(){
        return ResponseEntity.ok(professorRepository.findAll());
    }

    @PostMapping("/salvarNotas")
    public ResponseEntity<?> salvarNotas(@RequestBody NotasDTO notasDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(professorService.aplicarNotas(notasDTO));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar notas");
        }
    }
    
}
