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

import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.FaltaDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.dto.NotaDTO;
import kaua.sistema_gerenciamento_escolar.dto.ProfessorDTO;
import kaua.sistema_gerenciamento_escolar.service.AuthService;
import kaua.sistema_gerenciamento_escolar.service.ProfessorService;

@Controller
public class ProfessorController {
    
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AuthService authService;

    @GetMapping("/professor")
    public String indexProfessor(Model model){
        Integer id = authService.getIdAutenticado();

        ProfessorDTO professorResumo = professorService.professorInformacoes(id);
        List<MateriaDTO> materiasResumo = professorService.professorMaterias(id);
        List<NotaDTO> notaResumo = professorService.professorNotas(id);
        Set<FaltaDTO> faltaResumo = professorService.professorFaltas(id);
        List<AlunoDTO> alunoResumo = professorService.alunosProfessor(id);
        
        //converter para usar no thymeleaf
        List<FaltaDTO> faltaResumoList = new ArrayList<>(faltaResumo);
        model.addAttribute("alunos", alunoResumo);
        model.addAttribute("professor", professorResumo);
        model.addAttribute("materias", materiasResumo);
        model.addAttribute("notas", notaResumo);
        //ordenando a exibição pelo nome da matéria

        Collections.sort(faltaResumoList,Comparator.comparing(falta -> falta.getMateria().getNome())); 
        
        model.addAttribute("faltas", faltaResumoList);
        return "professor";
    }

    @GetMapping("/professor/professorTurmaGet/{materia_id}")
    public String professorTurmasGet(@PathVariable Integer materia_id, Model model){
        Integer id = authService.getIdAutenticado();

        List<AlunoDTO> alunoResumo = professorService.alunosProfessor(id);
        List<MateriaDTO> materiasResumo = professorService.professorMaterias(id);
        MateriaDTO materiaSelecionada = null;
        for (MateriaDTO materia : materiasResumo){
            if(materia.getId().equals(materia_id)){
                materiaSelecionada = materia;
                break;
            }
        }  
        model.addAttribute("materia", materiaSelecionada);
        model.addAttribute("alunos", alunoResumo);
        return "professorTurma";
    }

    @GetMapping("/professor/professorFaltasGet/{materia_id}")
    public String aplicarFaltaGet(@PathVariable Integer materia_id, Model model){
        Integer id = authService.getIdAutenticado();

        List<AlunoDTO> alunosResumo = professorService.alunosProfessor(id);
        model.addAttribute("alunos", alunosResumo);
        return "professorFaltas";
    }

    @GetMapping("/professor/professorNotasGet/{materia_id}")
    public String aplicarNotasGet(@PathVariable Integer materia_id, Model model){
        Integer id = authService.getIdAutenticado();

        List<AlunoDTO> alunosResumo = professorService.alunosProfessor(id);
        Map<Integer, NotaDTO> notasMap = new HashMap<>();
        for (AlunoDTO aluno : alunosResumo){
            for(NotaDTO nota: aluno.getHistorico()){
                if (nota.getMateria().getId() == materia_id){
                    notasMap.put(aluno.getId(), nota);
                }
            }
        }
        model.addAttribute("notasMap", notasMap);
        model.addAttribute("alunos", alunosResumo);
        return "professorNotas";
    }

    @GetMapping("/professor/professorMateriaGet")
    public String escolherMateriaFalta(Model model){
        Integer id = authService.getIdAutenticado();
        
        List<MateriaDTO> materiasResumo = professorService.professorMaterias(id);
        model.addAttribute("materias", materiasResumo);

        return "professorMateria";
    }

    @PostMapping("/professor/salvarNotas/{materia_id}")
    public String salvarNotas(@PathVariable Integer materia_id,@RequestParam("alunoId[]") List<Integer> alunosId, @RequestParam("nota1[]") List<Double> nota1, @RequestParam("nota2[]") List<Double> nota2){
        professorService.aplicarNotas(materia_id, alunosId, nota1, nota2);
        return "redirect:/professor";
    }

    @PostMapping("/professor/aplicarFalta/{materia_id}")
    public String aplicarFalta(@PathVariable int materia_id, @RequestParam("alunoId[]") List<Integer> alunosId, @RequestParam("quantidade[]") List<Integer> quantidades){
        professorService.aplicarFaltas(materia_id, alunosId, quantidades);
        return "redirect:/professor";
    }

    
}
