package kaua.sistema_gerenciamento_escolar.controller;

import java.util.List;
import java.util.Set;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoDTO);
    }

    @PostMapping("/salvarMateria")
    public ResponseEntity<?> criarMateria(@RequestBody MateriaDTO materiaDTO){
       return ResponseEntity.status(HttpStatus.CREATED).body(materiaDTO);
        
    }

    @PostMapping("/salvarProfessor")
    public ResponseEntity<?> criarProfessor(@RequestBody ProfessorDTO professorDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(professorDTO);
    }

    @PutMapping("/{aluno_id}/adicionarMateriaAluno")
    public ResponseEntity<?> adicionarMateriaAluno(@PathVariable Integer aluno_id, @RequestBody Set<Integer> materias_id){
        return ResponseEntity.ok(adminService.adicionarMateriaAluno(aluno_id, materias_id));
    }
}
