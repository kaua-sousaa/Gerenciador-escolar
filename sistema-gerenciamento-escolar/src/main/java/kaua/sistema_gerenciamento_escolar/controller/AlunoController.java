package kaua.sistema_gerenciamento_escolar.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.FaltaDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.dto.NotaDTO;
import kaua.sistema_gerenciamento_escolar.service.AlunoService;

@Controller
public class AlunoController {
    
    @Autowired
    private AlunoService alunoService;

    @GetMapping("/aluno")
    public String indexAluno(Model model){
        AlunoDTO alunoResumo = alunoService.alunoInformacoes(33);
        List<FaltaDTO> faltaResumo = alunoService.faltasHistorico(33);
        List<NotaDTO> notaResumo = alunoService.notaHistorico(33);
        List<MateriaDTO> materiasResumos = alunoService.materiasAluno(33);
        model.addAttribute("aluno", alunoResumo); //info aluno
        model.addAttribute("faltas", faltaResumo); //info faltas
        model.addAttribute("notas", notaResumo); // info notas
        model.addAttribute("materias", materiasResumos); //info de materias
        return "aluno";
    }
}
