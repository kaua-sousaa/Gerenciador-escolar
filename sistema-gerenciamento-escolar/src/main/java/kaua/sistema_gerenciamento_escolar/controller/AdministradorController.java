package kaua.sistema_gerenciamento_escolar.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.dto.ProfessorDTO;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.model.Professor;
import kaua.sistema_gerenciamento_escolar.service.AdminService;

@RestController
public class AdministradorController {
    
    @Autowired
    private AdminService adminService;


    @GetMapping("/")
    public ResponseEntity<List<Aluno>> getAluno(){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAlunos());
    }

    @PostMapping("/salvarAluno")
    public ResponseEntity<?> criarAluno(@RequestBody AlunoDTO alunoDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(adminService.criarAluno(alunoDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar aluno");
        }
    }

    @PostMapping("/salvarMateria")
    public ResponseEntity<?> criarMateria(@RequestBody MateriaDTO materiaDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(adminService.criarMateria(materiaDTO));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar materia");
        }
        
    }

    @PostMapping("/salvarProfessor")
    public ResponseEntity<?> criarProfessor(@RequestBody ProfessorDTO professorDTO){
        try{
            return ResponseEntity.ok(adminService.criarProfessor(professorDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar professor");
        }
    }

   
}
