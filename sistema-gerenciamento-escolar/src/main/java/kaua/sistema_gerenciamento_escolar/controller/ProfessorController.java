package kaua.sistema_gerenciamento_escolar.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.AlunoResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.FaltaResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.MateriasResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.NotaResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.ProfessorResumo;
import kaua.sistema_gerenciamento_escolar.model.Materias;
import kaua.sistema_gerenciamento_escolar.service.ProfessorService;

@Controller
public class ProfessorController {
    

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/professor")
    public String indexProfessor(Model model){
        ProfessorResumo professorResumo = professorService.professorInformacoes(5);
        List<MateriasResumo> materiasResumo = professorService.professorMaterias(5);
        List<NotaResumo> notaResumo = professorService.professorNotas(5);
        Set<FaltaResumo> faltaResumo = professorService.professorFaltas(5);
        List<AlunoResumo> alunoResumo = professorService.alunosProfessor(5);
        
        //converter para usar no thymeleaf
        List<FaltaResumo> faltaResumoList = new ArrayList<>(faltaResumo);
        model.addAttribute("alunos", alunoResumo);
        model.addAttribute("professor", professorResumo);
        model.addAttribute("materias", materiasResumo);
        model.addAttribute("notas", notaResumo);
        //ordenando a exibição pelo nome da matéria
        Collections.sort(faltaResumoList,Comparator.comparing(falta -> falta.getMateria().getNome()));
        model.addAttribute("faltas", faltaResumoList);
        return "professor";
    }

    @GetMapping("/professorFaltasGet/{materia_id}")
    public String aplicarFaltaGet(@PathVariable Integer materia_id, Model model){
        List<AlunoResumo> alunosResumo = professorService.alunosProfessor(5);
        model.addAttribute("alunos", alunosResumo);
        return "professorFaltas";
    }

    @GetMapping("/professorNotasGet/{materia_id}")
    public String aplicarNotasGet(@PathVariable Integer materia_id, Model model){
        List<AlunoResumo> alunosResumo = professorService.alunosProfessor(5);
        Map<Integer, NotaResumo> notasMap = new HashMap<>();
        for (AlunoResumo aluno : alunosResumo){
            for(NotaResumo nota: aluno.getHistorico()){
                if (nota.getMateria().getId() == materia_id){
                    notasMap.put(aluno.getId(), nota);
                }
            }
        }
        model.addAttribute("notasMap", notasMap);
        model.addAttribute("alunos", alunosResumo);
        return "professorNotas";
    }

    @GetMapping("/professorMateriaGet")
    public String escolherMateriaFalta(Model model){
        List<MateriasResumo> materiasResumo = professorService.professorMaterias(5);
        model.addAttribute("materias", materiasResumo);

        return "professorMateria";
    }

    @PostMapping("/salvarNotas/{materia_id}")
    public String salvarNotas(@PathVariable Integer materia_id,@RequestParam("alunoId[]") List<Integer> alunosId, @RequestParam("nota1[]") List<Double> nota1, @RequestParam("nota2[]") List<Double> nota2){
        professorService.aplicarNotas(materia_id, alunosId, nota1, nota2);
        return "redirect:/professor";
    }

    @PostMapping("/aplicarFalta/{materia_id}")
    public String aplicarFalta(@PathVariable int materia_id, @RequestParam("alunoId[]") List<Integer> alunosId, @RequestParam("quantidade[]") List<Integer> quantidades){
        professorService.aplicarFaltas(materia_id, alunosId, quantidades);
        return "redirect:/professor";
    }

    
}
