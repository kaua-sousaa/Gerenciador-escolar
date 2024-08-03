package kaua.sistema_gerenciamento_escolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import kaua.sistema_gerenciamento_escolar.service.AlunoService;

@RestController
public class AlunoController {
    
    @Autowired
    private AlunoService alunoService;

    @GetMapping("/{aluno_id}/alunoInformacoes")
    public ResponseEntity<?> alunoInfo(@PathVariable Integer aluno_id){
;
        return ResponseEntity.ok(alunoService.alunoInformacoes(aluno_id));
    }

    @GetMapping("/{aluno_id}/historicoNotas")
    public ResponseEntity<?> historicoNotasAluno(@PathVariable Integer aluno_id){
        
        return ResponseEntity.ok(alunoService.notaHistorico(aluno_id));
    }

    @GetMapping("/{aluno_id}/historicoFaltas")
    public ResponseEntity<?> historicoFaltasAluno(@PathVariable Integer aluno_id){
        return ResponseEntity.ok(alunoService.faltasHistorico(aluno_id));
    }
}
