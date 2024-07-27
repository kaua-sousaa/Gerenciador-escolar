package kaua.sistema_gerenciamento_escolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.repository.AlunoRepository;
import kaua.sistema_gerenciamento_escolar.service.AdminService;
import kaua.sistema_gerenciamento_escolar.service.AlunoService;

@RestController
public class AlunoController {
    
    @Autowired
    private AlunoService alunoService;

    @GetMapping("/{aluno_id}/alunoInformacoes")
    public ResponseEntity<Aluno> alunoInfo(@PathVariable Integer aluno_id){
        return ResponseEntity.ok(alunoService.alunoInformacoes(aluno_id));
    }
}
