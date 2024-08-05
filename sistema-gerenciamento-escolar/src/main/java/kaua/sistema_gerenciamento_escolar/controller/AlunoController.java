package kaua.sistema_gerenciamento_escolar.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.AlunoResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.FaltaResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.MateriasResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.NotaResumo;
import kaua.sistema_gerenciamento_escolar.service.AlunoService;

@Controller
public class AlunoController {
    
    @Autowired
    private AlunoService alunoService;

    @GetMapping("/{aluno_id}/alunoInformacoes")
    public ResponseEntity<?> alunoInfo(@PathVariable Integer aluno_id){

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

    @GetMapping("/aluno")
    public String indexAluno(Model model){
        AlunoResumo alunoResumo = alunoService.alunoInformacoes(23);
        List<FaltaResumo> faltaResumo = alunoService.faltasHistorico(23).stream().limit(2).collect(Collectors.toList());
        List<NotaResumo> notaResumo = alunoService.notaHistorico(23).stream().limit(2).collect(Collectors.toList());
        List<MateriasResumo> materiasResumos = alunoService.materiasAluno(23).stream().limit(3).collect(Collectors.toList());
        model.addAttribute("aluno", alunoResumo); //info aluno
        model.addAttribute("faltas", faltaResumo); //info faltas
        model.addAttribute("notas", notaResumo); // info notas
        model.addAttribute("materias", materiasResumos); //info de materias
        return "aluno";
    }
}
