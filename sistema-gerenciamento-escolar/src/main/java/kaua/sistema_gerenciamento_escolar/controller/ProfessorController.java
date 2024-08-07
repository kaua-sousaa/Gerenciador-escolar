package kaua.sistema_gerenciamento_escolar.controller;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import kaua.sistema_gerenciamento_escolar.dto.FaltasDTO;
import kaua.sistema_gerenciamento_escolar.dto.NotasDTO;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.AlunoResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.FaltaResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.MateriasResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.NotaResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.ProfessorResumo;
import kaua.sistema_gerenciamento_escolar.model.Professor;
import kaua.sistema_gerenciamento_escolar.repository.ProfessorRepository;
import kaua.sistema_gerenciamento_escolar.service.ProfessorService;

@Controller
public class ProfessorController {
    
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/professor")
    public String indexProfessor(Model model){
        ProfessorResumo professorResumo = professorService.professorInformacoes(5);
        List<MateriasResumo> materiasResumo = professorService.professorMaterias(5);
        List<NotaResumo> notaResumo = professorService.professorNotas(5);
        Set<FaltaResumo> faltaResumo = professorService.professorFaltas(5);
        model.addAttribute("professor", professorResumo);
        model.addAttribute("materias", materiasResumo);
        model.addAttribute("notas", notaResumo);
        model.addAttribute("faltas", faltaResumo);
        return "professor";
    }

    @GetMapping("/professorFaltas/{materia_id}")
    public String aplicarFaltaGet(@PathVariable Integer materia_id, Model model){
        List<AlunoResumo> alunosResumo = professorService.alunosProfessor(5);
       // Set<FaltaResumo> faltaResumo = professorService.professorFaltas(5);
       // model.addAttribute("faltas", faltaResumo);
        model.addAttribute("alunos", alunosResumo);
        return "professorFaltas";
    }

    @GetMapping("/professorMateriaFaltaGet")
    public String escolherMateriaFalta(Model model){
        List<MateriasResumo> materiasResumo = professorService.professorMaterias(5);
        model.addAttribute("materias", materiasResumo);

        return "professorMateriaFalta";
    }

    @PutMapping("/salvarNotas")
    public ResponseEntity<?> salvarNotas(@RequestBody NotasDTO notasDTO){
       return ResponseEntity.ok(professorService.aplicarNotas(notasDTO));
    }
    
    @PutMapping("/{id}/alterarNota")
    public ResponseEntity<?> alterarNota(@PathVariable Integer id, @RequestBody List<Double> notas){
        return ResponseEntity.ok(professorService.alterarNotas(id, notas));
    }

    @PostMapping("/aplicarFalta/{materia_id}")
    public String aplicarFalta(@PathVariable int materia_id, @RequestParam("alunoId[]") List<Integer> alunosId, @RequestParam("quantidade[]") List<Integer> quantidades){
        professorService.aplicarFaltas(materia_id, alunosId, quantidades);
        return "redirect:/professor";
    }

    
}
